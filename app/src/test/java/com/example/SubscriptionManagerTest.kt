package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.MediaRepository
import com.example.util.SubscriptionCheckResult
import com.example.util.SubscriptionManager
import com.example.util.UserSubscriptionRecord
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.util.Calendar
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class SubscriptionManagerTest {

    private fun dateOf(year: Int, month: Int, day: Int): java.util.Date {
        return Calendar.getInstance(Locale.US).apply {
            set(year, month - 1, day, 14, 30, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
    }

    @Test
    fun `isDateValid checks YYYY-MM-DD boundary against current device date`() {
        val expiry = "2026-12-17"
        assertTrue(SubscriptionManager.isDateValid(expiry, dateOf(2026, 11, 18)))
        assertTrue(SubscriptionManager.isDateValid(expiry, dateOf(2026, 12, 17)))
        assertFalse(SubscriptionManager.isDateValid(expiry, dateOf(2026, 12, 18)))
        assertFalse(SubscriptionManager.isDateValid("2026-02-30", dateOf(2026, 2, 1)))
        assertFalse(SubscriptionManager.isDateValid("", dateOf(2026, 11, 18)))
        assertFalse(SubscriptionManager.isDateValid(null, dateOf(2026, 11, 18)))
    }

    @Test
    fun `normalizePhone cleans country code and formatting`() {
        assertEquals("01960454682", SubscriptionManager.normalizePhone("+8801960454682"))
        assertEquals("01960454682", SubscriptionManager.normalizePhone("01960-454 682"))
        assertEquals("01960454682", SubscriptionManager.normalizePhone("8801960454682"))
    }

    @Test
    fun `evaluateRecord enforces active status and unexpired date`() {
        val activeRec = UserSubscriptionRecord(
            phone = "01960454682",
            subscriptionStatus = "active",
            expiryDate = "2026-12-17",
            packageType = "1Month",
            voucherCode = "8936"
        )
        val resActive = SubscriptionManager.evaluateRecord(activeRec, dateOf(2026, 11, 18))
        assertTrue(resActive is SubscriptionCheckResult.Active)
        assertEquals(29L, (resActive as SubscriptionCheckResult.Active).remainingDays)

        val resExpired = SubscriptionManager.evaluateRecord(activeRec, dateOf(2026, 12, 18))
        assertTrue(resExpired is SubscriptionCheckResult.ExpiredOrInactive)

        val inactiveRec = activeRec.copy(subscriptionStatus = "suspended")
        val resInactive = SubscriptionManager.evaluateRecord(inactiveRec, dateOf(2026, 11, 18))
        assertTrue(resInactive is SubscriptionCheckResult.ExpiredOrInactive)
    }

    @Test
    fun `repository verifies voucher and checks startup subscription from RTDB path users_phone`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = MediaRepository(context)
        repo.clearSubscriberSession()

        val futureExpiry = SubscriptionManager.addMonthsToDateString(1)
        val expiredDate = "2020-01-01"

        val server = ServerSocket(0)
        val port = server.localPort
        var serveExpired = false

        val serverThread = Thread {
            try {
                while (!server.isClosed) {
                    val socket = server.accept()
                    socket.use { s ->
                        val reader = BufferedReader(InputStreamReader(s.getInputStream()))
                        val requestLine = reader.readLine().orEmpty()
                        while (true) {
                            val h = reader.readLine() ?: break
                            if (h.isEmpty()) break
                        }
                        val path = requestLine.split(" ").getOrNull(1).orEmpty()
                        val jsonBody = when {
                            path.startsWith("/subscription_settings.json") ->
                                """{"gate_enabled":true,"support_contact":"WhatsApp 01900000000"}"""
                            path.startsWith("/users/01960454682.json") -> {
                                val exp = if (serveExpired) expiredDate else futureExpiry
                                """{"subscription_status":"active","expiry_date":"$exp","package_type":"1Month","voucher_code":"8936"}"""
                            }
                            else -> "null"
                        }
                        val resp = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: ${jsonBody.toByteArray().size}\r\nConnection: close\r\n\r\n$jsonBody"
                        s.getOutputStream().write(resp.toByteArray())
                        s.getOutputStream().flush()
                    }
                }
            } catch (_: Exception) {}
        }
        serverThread.isDaemon = true
        serverThread.start()

        try {
            repo.saveFirebaseUrl("http://127.0.0.1:$port")
            repo.firestoreApiOrigin = "http://127.0.0.1:$port"

            // 1. Initial startup without saved phone asks for phone/voucher
            val initialStartup = repo.checkUserSubscriptionOnStartup()
            assertTrue(initialStartup is SubscriptionCheckResult.NeedsPhoneOrVoucher)

            // 2. Wrong voucher fails
            val (wrongOk, _) = repo.verifyAndActivateSubscription("01960454682", "0000")
            assertFalse(wrongOk)

            // 3. Correct phone + voucher succeeds and persists session
            val (ok, res) = repo.verifyAndActivateSubscription("01960454682", "8936")
            assertTrue(ok)
            assertTrue(res is SubscriptionCheckResult.Active)
            assertEquals("01960454682", repo.getSavedSubscriberPhone())

            // 4. Subsequent app open checks /users/01960454682 and grants dashboard access
            val startupActive = repo.checkUserSubscriptionOnStartup()
            assertTrue(startupActive is SubscriptionCheckResult.Active)

            // 5. Once expiry_date passes, startup check blocks with ExpiredOrInactive
            serveExpired = true
            val startupExpired = repo.checkUserSubscriptionOnStartup()
            assertTrue(startupExpired is SubscriptionCheckResult.ExpiredOrInactive)
        } finally {
            server.close()
        }
    }

    @Test
    fun `when RTDB returns HTTP 401 Unauthorized, Firestore dual-sync succeeds for admin CRUD and startup check`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = MediaRepository(context)
        repo.clearSubscriberSession()

        val futureExpiry = SubscriptionManager.addMonthsToDateString(1)
        val firestoreDocs = java.util.concurrent.ConcurrentHashMap<String, String>()
        val server = ServerSocket(0)
        val port = server.localPort

        val serverThread = Thread {
            try {
                while (!server.isClosed) {
                    val socket = server.accept()
                    socket.use { s ->
                        val input = s.getInputStream()
                        val headerBytes = java.io.ByteArrayOutputStream()
                        var cur: Int
                        var headerEnd = false
                        while (!headerEnd) {
                            cur = input.read()
                            if (cur == -1) break
                            headerBytes.write(cur)
                            val len = headerBytes.size()
                            if (len >= 4) {
                                val arr = headerBytes.toByteArray()
                                if (arr[len - 4] == '\r'.code.toByte() && arr[len - 3] == '\n'.code.toByte() &&
                                    arr[len - 2] == '\r'.code.toByte() && arr[len - 1] == '\n'.code.toByte()
                                ) {
                                    headerEnd = true
                                }
                            }
                        }
                        val headerText = headerBytes.toString("UTF-8")
                        val lines = headerText.split("\r\n")
                        val requestLine = lines.firstOrNull().orEmpty()
                        val parts = requestLine.split(" ")
                        val method = parts.getOrNull(0).orEmpty().uppercase()
                        val rawPath = parts.getOrNull(1).orEmpty().substringBefore("?")
                        val contentLength = lines.firstOrNull { it.startsWith("Content-Length:", ignoreCase = true) }
                            ?.substringAfter(":")?.trim()?.toIntOrNull() ?: 0

                        val bodyBytes = ByteArray(contentLength)
                        var readTotal = 0
                        while (readTotal < contentLength) {
                            val r = input.read(bodyBytes, readTotal, contentLength - readTotal)
                            if (r <= 0) break
                            readTotal += r
                        }
                        val reqBody = String(bodyBytes, Charsets.UTF_8)

                        val (statusLine, respBody) = when {
                            // Simulate locked legacy RTDB returning HTTP 401 Unauthorized on every path
                            rawPath.endsWith(".json") -> {
                                "401 Unauthorized" to """{"error":"Permission denied"}"""
                            }
                            // Firestore :runQuery for users collection
                            rawPath.endsWith("/documents:runQuery") -> {
                                val arr = org.json.JSONArray()
                                firestoreDocs.entries.filter { it.key.startsWith("users/") }.forEach { (docKey, docJson) ->
                                    val docObj = org.json.JSONObject(docJson)
                                    arr.put(org.json.JSONObject().put("document", org.json.JSONObject().apply {
                                        put("name", "projects/test/databases/(default)/documents/$docKey")
                                        put("fields", docObj.optJSONObject("fields") ?: org.json.JSONObject())
                                    }))
                                }
                                if (arr.length() == 0) {
                                    arr.put(org.json.JSONObject().put("readTime", "2026-11-18T00:00:00Z"))
                                }
                                "200 OK" to arr.toString()
                            }
                            // Firestore document endpoints: /v1/projects/.../databases/.../documents/{docPath}
                            rawPath.contains("/documents/") -> {
                                val docKey = rawPath.substringAfter("/documents/")
                                when (method) {
                                    "PATCH", "POST" -> {
                                        firestoreDocs[docKey] = reqBody
                                        "200 OK" to reqBody
                                    }
                                    "GET" -> {
                                        val stored = firestoreDocs[docKey]
                                        if (stored != null) "200 OK" to stored
                                        else "404 Not Found" to """{"error":{"code":404,"message":"Document not found"}}"""
                                    }
                                    "DELETE" -> {
                                        firestoreDocs.remove(docKey)
                                        "200 OK" to "{}"
                                    }
                                    else -> "200 OK" to "{}"
                                }
                            }
                            else -> "404 Not Found" to "{}"
                        }

                        val resp = "HTTP/1.1 $statusLine\r\nContent-Type: application/json\r\nContent-Length: ${respBody.toByteArray().size}\r\nConnection: close\r\n\r\n$respBody"
                        s.getOutputStream().write(resp.toByteArray())
                        s.getOutputStream().flush()
                    }
                }
            } catch (_: Exception) {}
        }
        serverThread.isDaemon = true
        serverThread.start()

        try {
            repo.saveFirebaseUrl("http://127.0.0.1:$port")
            repo.firestoreApiOrigin = "http://127.0.0.1:$port"

            // 1. Save subscriber from Admin Panel even when RTDB returns 401
            val subRecord = UserSubscriptionRecord(
                phone = "01640541273",
                subscriptionStatus = "active",
                expiryDate = futureExpiry,
                packageType = "1Month",
                voucherCode = "1273"
            )
            val (saveOk, saveMsg) = repo.saveUserSubscriptionToRtdb(subRecord)
            assertTrue("Expected Firestore fallback save to succeed, got: $saveMsg", saveOk)

            // 2. Save settings even when RTDB returns 401
            val (settingsOk, settingsMsg) = repo.saveSubscriptionSettingsToRtdb(true, "WhatsApp: 01640541273")
            assertTrue("Expected settings save to succeed via Firestore, got: $settingsMsg", settingsOk)

            // 3. List subscribers in Admin Panel
            val users = repo.fetchAllSubscribedUsersFromRtdb()
            assertEquals(1, users.size)
            assertEquals("01640541273", users[0].phone)
            assertEquals("1273", users[0].voucherCode)

            // 4. Verify & activate via user phone + voucher code despite RTDB 401
            val (verifyOk, verifyRes) = repo.verifyAndActivateSubscription("01640541273", "1273")
            assertTrue(verifyOk)
            assertTrue(verifyRes is SubscriptionCheckResult.Active)

            // 5. App startup check succeeds via Firestore
            val startupRes = repo.checkUserSubscriptionOnStartup()
            assertTrue(startupRes is SubscriptionCheckResult.Active)

            // 6. Delete user from Admin Panel succeeds via Firestore
            val (delOk, _) = repo.deleteUserSubscriptionFromRtdb("01640541273")
            assertTrue(delOk)
            assertTrue(repo.fetchAllSubscribedUsersFromRtdb().isEmpty())
        } finally {
            server.close()
        }
    }
}

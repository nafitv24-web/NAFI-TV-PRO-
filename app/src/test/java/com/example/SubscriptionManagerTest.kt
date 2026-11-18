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
}

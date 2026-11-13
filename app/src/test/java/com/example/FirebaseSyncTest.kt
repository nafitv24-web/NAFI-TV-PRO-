package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.FirebaseAuthManager
import com.example.data.MediaRepository
import com.example.model.AppNotification
import com.example.model.AppUpdateInfo
import com.example.model.CloudStreamRepo
import com.example.model.MediaItem
import com.example.model.MediaType
import com.example.model.MovieProvider
import com.example.model.NotificationType
import com.sun.net.httpserver.HttpServer
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.InetSocketAddress
import java.util.concurrent.CopyOnWriteArrayList

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class FirebaseSyncTest {

    private lateinit var context: Context
    private lateinit var server: HttpServer
    private var baseUrl: String = ""

    private data class RecordedRequest(
        val method: String,
        val pathAndQuery: String,
        val authHeader: String?,
        val body: String
    )

    private val recordedRequests = CopyOnWriteArrayList<RecordedRequest>()
    private var handlerOverride: ((String, String, String?, String) -> Pair<Int, String>)? = null

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.getSharedPreferences("nafitv24_firebase_auth", Context.MODE_PRIVATE).edit().clear().commit()
        context.getSharedPreferences("nafitv_prefs", Context.MODE_PRIVATE).edit().clear().commit()
        recordedRequests.clear()
        handlerOverride = null

        server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/") { exchange ->
            val reqBody = exchange.requestBody.bufferedReader(Charsets.UTF_8).readText()
            val query = exchange.requestURI.rawQuery
            val fullPath = if (query.isNullOrBlank()) exchange.requestURI.path else "${exchange.requestURI.path}?$query"
            val authHeader = exchange.requestHeaders.getFirst("Authorization")
            recordedRequests.add(RecordedRequest(exchange.requestMethod, fullPath, authHeader, reqBody))

            val (status, respBody) = handlerOverride?.invoke(exchange.requestMethod, fullPath, authHeader, reqBody)
                ?: Pair(200, "{}")

            val bytes = respBody.toByteArray(Charsets.UTF_8)
            exchange.responseHeaders.add("Content-Type", "application/json; charset=utf-8")
            exchange.sendResponseHeaders(status, bytes.size.toLong())
            exchange.responseBody.use { it.write(bytes) }
        }
        server.start()
        baseUrl = "http://127.0.0.1:${server.address.port}"
    }

    @After
    fun tearDown() {
        server.stop(0)
    }

    @Test
    fun `local pin fallback has no cloud token and returns null from getValidIdToken`() = runBlocking {
        val repo = MediaRepository(context)
        val authPrefs = context.getSharedPreferences("nafitv24_firebase_auth", Context.MODE_PRIVATE)
        authPrefs.edit()
            .putString("auth_id_token", "local_admin_session_12345")
            .putString("auth_email", FirebaseAuthManager.PRIMARY_ADMIN_EMAIL)
            .putString("auth_uid", "admin_local")
            .putLong("auth_expiry_timestamp", System.currentTimeMillis() + 3600_000L)
            .commit()

        assertTrue(repo.authManager.isUserLoggedIn())
        assertTrue(repo.authManager.isAuthorizedAdmin())
        assertFalse(repo.authManager.hasCloudAuthToken())
        assertNull(repo.authManager.getValidIdToken())
    }

    @Test
    fun `google jwt enables cloud auth but never leaks primary jwt to legacy rtdb`() = runBlocking {
        val repo = MediaRepository(context)
        val jwt = "eyJhbGciOiJSUzI1NiJ9.eyJlbWFpbCI6Im5hZml0djI0QGdtYWlsLmNvbSJ9.signature"
        repo.authManager.saveGoogleSession(jwt, FirebaseAuthManager.PRIMARY_ADMIN_EMAIL, "uid_google_admin")

        assertTrue(repo.authManager.hasCloudAuthToken())
        assertEquals(jwt, repo.authManager.getValidIdToken())
        assertNull(repo.authManager.getRtdbTokenForUrl("https://nafitv24-live-default-rtdb.firebaseio.com/"))

        val legacyJwt = "eyJhbGciOiJSUzI1NiJ9.eyJwcm9qZWN0IjoibGVnYWN5In0.legacySig"
        context.getSharedPreferences("nafitv24_firebase_auth", Context.MODE_PRIVATE)
            .edit()
            .putString("legacy_auth_id_token", legacyJwt)
            .putLong("legacy_auth_expiry_timestamp", System.currentTimeMillis() + 3600_000L)
            .commit()

        assertEquals(legacyJwt, repo.authManager.getRtdbTokenForUrl("https://nafitv24-live-default-rtdb.firebaseio.com/"))
    }

    @Test
    fun `pushAppUpdateInfo syncs firestore and rtdb with bearer header and fetchAppUpdateInfo picks newest timestamp`() = runBlocking {
        val repo = MediaRepository(context)
        repo.firestoreApiOrigin = baseUrl
        repo.saveFirebaseUrl(baseUrl)
        val jwt = "hdr.pay.sig"
        repo.authManager.saveGoogleSession(jwt, FirebaseAuthManager.PRIMARY_ADMIN_EMAIL, "uid_1")

        var storedFirestoreUpdateBody: String? = null
        handlerOverride = { method, path, _, body ->
            when {
                method == "PATCH" && path.contains("settings/app_update") -> {
                    storedFirestoreUpdateBody = body
                    Pair(200, body)
                }
                method == "GET" && path.contains("settings/app_update") -> {
                    Pair(200, storedFirestoreUpdateBody ?: "{}")
                }
                method == "GET" && path.endsWith("/app_update.json") -> {
                    // Simulate stale RTDB record older than Firestore updated_at
                    Pair(200, """{"versionCode":26,"versionName":"3.1.1","downloadUrl":"https://old.apk","updated_at":1000}""")
                }
                else -> Pair(200, "{}")
            }
        }

        val info = AppUpdateInfo(
            versionCode = 32,
            versionName = "3.1.6",
            downloadUrl = "https://cdn.example.com/nafitv_v3.1.6.apk",
            releaseNotes = "Admin Firebase sync fix",
            isForceUpdate = true,
            apkSize = "12.4 MB",
            releaseDate = "13 Nov 2026"
        )

        val pushOk = repo.pushAppUpdateInfo(info, baseUrl)
        assertTrue(pushOk)
        assertTrue(recordedRequests.any {
            it.method == "PATCH" &&
                it.pathAndQuery.contains("/documents/settings/app_update") &&
                it.authHeader == "Bearer $jwt"
        })
        assertTrue(recordedRequests.any {
            it.method == "PUT" && it.pathAndQuery.startsWith("/app_update.json")
        })

        val fetched = repo.fetchAppUpdateInfo(baseUrl)
        assertNotNull(fetched)
        assertEquals(32, fetched?.versionCode)
        assertEquals("3.1.6", fetched?.versionName)
        assertEquals("https://cdn.example.com/nafitv_v3.1.6.apk", fetched?.downloadUrl)
    }

    @Test
    fun `pushAppUpdateInfo returns false and sets lastFirebaseError on 403 permission denied`() = runBlocking {
        val repo = MediaRepository(context)
        repo.firestoreApiOrigin = baseUrl
        repo.saveFirebaseUrl(baseUrl)
        repo.authManager.saveGoogleSession("hdr.pay.sig", FirebaseAuthManager.PRIMARY_ADMIN_EMAIL, "uid_1")

        handlerOverride = { _, _, _, _ ->
            Pair(403, """{"error":{"message":"Missing or insufficient permissions"}}""")
        }

        val pushOk = repo.pushAppUpdateInfo(
            AppUpdateInfo(versionCode = 33, versionName = "3.1.7", downloadUrl = "https://example.com/app.apk"),
            baseUrl
        )
        assertFalse(pushOk)
        assertTrue(repo.lastFirebaseError.isNotBlank())
    }

    @Test
    fun `all admin write operations attach bearer header and report false on backend rejection`() = runBlocking {
        val repo = MediaRepository(context)
        repo.firestoreApiOrigin = baseUrl
        repo.saveFirebaseUrl(baseUrl)
        val jwt = "admin.jwt.token"
        repo.authManager.saveGoogleSession(jwt, FirebaseAuthManager.PRIMARY_ADMIN_EMAIL, "uid_admin")

        // 1. Rejection scenario -> must return false, never fake true
        handlerOverride = { _, _, _, _ -> Pair(403, """{"error":"Permission denied"}""") }

        val mediaItem = MediaItem(
            id = "tv_test_1",
            title = "Test TV",
            category = "Bangla",
            type = MediaType.LIVE_TV,
            streamUrl = "https://example.com/live.m3u8"
        )
        val pushItemFail = repo.pushToFirebase(mediaItem, baseUrl)
        assertFalse(pushItemFail.first)
        assertTrue(pushItemFail.second.isNotBlank())

        assertFalse(repo.pushAppConfigToFirebase(liveTvM3u = "https://example.com/tv.m3u", url = baseUrl))

        val testRepo = CloudStreamRepo(
            id = "repo_1",
            name = "Test Repo",
            repoUrl = "https://example.com/repo.json",
            providers = listOf(MovieProvider(id = "p1", name = "Site 1", siteUrl = "https://site1.example.com"))
        )
        assertFalse(repo.pushCloudStreamReposToFirebase(listOf(testRepo), baseUrl))

        val notif = AppNotification(title = "Test Notice", message = "Hello", type = NotificationType.BROADCAST)
        val broadcastFail = repo.sendBroadcastNotification(notif, pushToCloud = true)
        assertFalse(broadcastFail.first)
        assertTrue(broadcastFail.second.isNotBlank())

        // 2. Authorized success scenario -> Bearer token attached and returns true
        recordedRequests.clear()
        handlerOverride = { _, _, _, _ -> Pair(200, "{}") }

        assertTrue(repo.pushToFirebase(mediaItem, baseUrl).first)
        assertTrue(repo.pushAppConfigToFirebase(liveTvM3u = "https://example.com/tv.m3u", url = baseUrl))
        assertTrue(repo.pushCloudStreamReposToFirebase(listOf(testRepo), baseUrl))
        assertTrue(repo.sendBroadcastNotification(notif, pushToCloud = true).first)

        assertTrue(recordedRequests.any {
            it.method == "PATCH" && it.pathAndQuery.contains("/channels/tv_test_1") && it.authHeader == "Bearer $jwt"
        })
        assertTrue(recordedRequests.any {
            it.method == "PATCH" && it.pathAndQuery.contains("/settings/app_config") && it.authHeader == "Bearer $jwt"
        })
        assertTrue(recordedRequests.any {
            it.method == "PATCH" && it.pathAndQuery.contains("/settings/cloudstream_repos") && it.authHeader == "Bearer $jwt"
        })
        assertTrue(recordedRequests.any {
            it.method == "PATCH" && it.pathAndQuery.contains("/notifications/") && it.authHeader == "Bearer $jwt"
        })
    }
}

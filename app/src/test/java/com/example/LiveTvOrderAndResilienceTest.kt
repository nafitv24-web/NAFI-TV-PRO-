package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.MediaRepository
import com.example.model.MediaItem
import com.example.model.MediaType
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.util.concurrent.atomic.AtomicInteger

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class LiveTvOrderAndResilienceTest {

    private fun ch(id: String, title: String, url: String, cat: String = "Bangla"): MediaItem {
        return MediaItem(
            id = id,
            title = title,
            category = cat,
            type = MediaType.LIVE_TV,
            streamUrl = url,
            isLive = true
        )
    }

    @Test
    fun `getSavedLiveTvM3uUrl strictly preserves Admin Panel top-to-bottom playlist order`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = MediaRepository(context)

        val adminInput = """
            https://top-priority.example.com/first.m3u
            https://second.example.com/second.m3u
            https://raw.githubusercontent.com/nafitv24-web/NAFI-TV/refs/heads/main/Update%20Channel.m3u
        """.trimIndent()

        repo.saveLiveTvM3uUrl(adminInput)
        val extracted = repo.extractUrls(repo.getSavedLiveTvM3uUrl())

        assertEquals(3, extracted.size)
        assertEquals("https://top-priority.example.com/first.m3u", extracted[0])
        assertEquals("https://second.example.com/second.m3u", extracted[1])
        assertEquals("https://raw.githubusercontent.com/nafitv24-web/NAFI-TV/refs/heads/main/Update%20Channel.m3u", extracted[2])
    }

    @Test
    fun `extractUrls preserves commas inside query params while splitting playlist URLs`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = MediaRepository(context)

        val input = "https://panel.example.com/get.php?type=m3u&cats=1,2,3, https://second.example.com/list.m3u\nhttps://third.example.com/tv.m3u"
        val urls = repo.extractUrls(input)

        assertEquals(3, urls.size)
        assertEquals("https://panel.example.com/get.php?type=m3u&cats=1,2,3", urls[0])
        assertEquals("https://second.example.com/list.m3u", urls[1])
        assertEquals("https://third.example.com/tv.m3u", urls[2])
    }

    @Test
    fun `assembleOrderedLiveTvChannels keeps topmost Admin playlist channels at the top regardless of fetch completion order`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = MediaRepository(context)

        val url1 = "https://pl1.example.com/top.m3u"
        val url2 = "https://pl2.example.com/mid.m3u"
        val url3 = "https://pl3.example.com/bottom.m3u"

        // Simulate Playlist #3 finishing FIRST, Playlist #2 SECOND, Playlist #1 LAST
        val batches = LinkedHashMap<String, List<MediaItem>>()
        batches[url3] = listOf(
            ch("p3_1", "Channel C1", "http://c1.live/1.m3u8"),
            ch("p3_2", "Somoy TV", "http://c2.live/somoy_backup.m3u8")
        )
        batches[url2] = listOf(
            ch("p2_1", "Channel B1", "http://b1.live/1.m3u8"),
            ch("p2_2", "Channel B2", "http://b2.live/2.m3u8")
        )
        batches[url1] = listOf(
            ch("p1_1", "Somoy TV", "http://a1.live/somoy_main.m3u8"),
            ch("p1_2", "Channel A2", "http://a2.live/2.m3u8")
        )

        val ordered = repo.assembleOrderedLiveTvChannels(
            orderedAdminUrls = listOf(url1, url2, url3),
            batchesByUrl = batches,
            xtreamChannels = listOf(ch("xt_1", "Xtream Z1", "http://xt.live/1.ts"))
        )

        val titles = ordered.map { it.title }
        assertEquals(
            listOf("Somoy TV", "Channel A2", "Channel B1", "Channel B2", "Channel C1", "Xtream Z1"),
            titles
        )
        // Somoy TV from top playlist is primary server, and lower playlist URL is merged as backup server
        val somoy = ordered.first { it.title == "Somoy TV" }
        assertEquals("http://a1.live/somoy_main.m3u8", somoy.servers[0].url)
        assertEquals(2, somoy.servers.size)
        assertEquals("http://c2.live/somoy_backup.m3u8", somoy.servers[1].url)
    }

    @Test
    fun `fetchSingleLiveTvSourceUrlFast retries transient slow-network failures and loads every single channel`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = MediaRepository(context)

        val requestCount = AtomicInteger(0)
        val server = ServerSocket(0)
        val port = server.localPort

        // Build a playlist with 5 channels including tvg-name fallback and protocol-relative URL
        val m3uPayload = buildString {
            appendLine("#EXTM3U")
            appendLine("#EXTINF:-1 tvg-name=\"BTV National\" group-title=\"Bangladesh\",BTV National")
            appendLine("http://stream.example.com/btv.m3u8")
            appendLine("#EXTINF:-1 tvg-name=\"Channel I\" group-title=\"Bangladesh\",")
            appendLine("http://stream.example.com/channeli.m3u8")
            appendLine("#EXTINF:-1 group-title=\"Sports\",T Sports HD")
            appendLine("//cdn.example.com/tsports.m3u8")
            appendLine("#EXTINF:-1 group-title=\"News\",Jamuna TV")
            appendLine("http://rgkkw.live/live/user/pass/151.ts")
            appendLine("#EXTINF:-1 group-title=\"Movies 24x7\",Bangla Cinema 24x7")
            appendLine("http://stream.example.com/cinema247.mp4")
        }

        val serverThread = Thread {
            try {
                while (!server.isClosed) {
                    val socket = server.accept()
                    socket.use { s ->
                        val reader = BufferedReader(InputStreamReader(s.getInputStream()))
                        reader.readLine()
                        while (true) {
                            val h = reader.readLine() ?: break
                            if (h.isEmpty()) break
                        }
                        val n = requestCount.incrementAndGet()
                        val resp = if (n == 1) {
                            // First attempt simulates slow/overloaded server returning 503
                            "HTTP/1.1 503 Service Unavailable\r\nContent-Length: 5\r\nConnection: close\r\n\r\nRetry"
                        } else {
                            val bytes = m3uPayload.toByteArray(Charsets.UTF_8)
                            "HTTP/1.1 200 OK\r\nContent-Type: audio/x-mpegurl\r\nContent-Length: ${bytes.size}\r\nConnection: close\r\n\r\n$m3uPayload"
                        }
                        s.getOutputStream().write(resp.toByteArray(Charsets.UTF_8))
                        s.getOutputStream().flush()
                    }
                }
            } catch (_: Exception) {}
        }
        serverThread.isDaemon = true
        serverThread.start()

        try {
            val url = "http://127.0.0.1:$port/live_all.m3u"
            val channels = repo.fetchSingleLiveTvSourceUrlFast(url, allowCacheFallback = false)

            assertTrue("Expected retry after 503 (count=${requestCount.get()})", requestCount.get() >= 2)
            assertEquals("Every single channel in the playlist must be loaded", 5, channels.size)
            assertEquals(
                listOf("BTV National", "Channel I", "T Sports HD", "Jamuna TV", "Bangla Cinema 24x7"),
                channels.map { it.title }
            )
            assertEquals("https://cdn.example.com/tsports.m3u8", channels[2].streamUrl)
        } finally {
            server.close()
        }
    }
}

package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.MediaRepository
import com.example.model.MediaItem
import com.example.model.MediaType
import com.example.model.PlaylistInfo
import com.example.model.StreamServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class LiveTvPlaylistUnificationTest {

    private lateinit var context: Context
    private lateinit var repository: MediaRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        MediaRepository.clearMemoryCaches()
        context.getSharedPreferences("nafitv_prefs", Context.MODE_PRIVATE).edit().clear().commit()
        context.filesDir.listFiles()?.forEach { file ->
            if (file.name.startsWith("cache_")) {
                file.delete()
            }
        }
        java.io.File(context.cacheDir, "media_cache").listFiles()?.forEach { file ->
            file.delete()
        }
        repository = MediaRepository(context)
    }

    @Test
    fun `getInitialLiveTv strictly excludes Playlist tab channels`() {
        val toffeePl = PlaylistInfo(
            id = "pl_toffee_1",
            title = "Toffee 1",
            url = "https://example.com/toffee.m3u",
            type = "M3U",
            isAdmin = true
        )
        repository.saveAdminPlaylist(toffeePl)
        repository.saveCachedPlaylistChannels(
            toffeePl.id,
            listOf(
                MediaItem(
                    id = "playlist_only_toffee_tv",
                    title = "Playlist Exclusive Toffee TV",
                    category = "Family",
                    type = MediaType.LIVE_TV,
                    streamUrl = "https://cdn.example.com/toffee_exclusive.m3u8",
                    isLive = true
                )
            )
        )

        val liveTvFromAdminSource = listOf(
            MediaItem(
                id = "admin_tv_1",
                title = "Admin Channel One",
                category = "Live TV",
                type = MediaType.LIVE_TV,
                streamUrl = "https://cdn.example.com/admin_one.m3u8",
                isLive = true
            )
        )
        repository.saveCachedLiveTvChannels(liveTvFromAdminSource)

        val initialLiveTv = repository.getInitialLiveTv()
        val titles = initialLiveTv.map { it.title }.toSet()

        assertTrue(titles.contains("Admin Channel One"))
        assertFalse(titles.contains("Playlist Exclusive Toffee TV"))
    }

    @Test
    fun `saveLiveTvM3uUrl preserves cache when unchanged and invalidates only when modified`() {
        val urlSet1 = "https://example.com/live1.m3u\nhttps://example.com/live2.m3u"
        repository.saveLiveTvM3uUrl(urlSet1)
        repository.saveCachedLiveTvChannels(
            listOf(
                MediaItem(
                    id = "ch_cached_1",
                    title = "Cached Admin Channel",
                    category = "Sports",
                    type = MediaType.LIVE_TV,
                    streamUrl = "https://cdn.example.com/cached.m3u8",
                    isLive = true
                )
            )
        )

        // Calling save with the exact same URL set (e.g. during Firebase app_config sync) must keep disk cache intact
        repository.saveLiveTvM3uUrl(urlSet1)
        assertEquals(1, repository.getCachedLiveTvChannels().size)

        // Modifying the Admin Live TV URLs must clear stale cache
        repository.saveLiveTvM3uUrl("$urlSet1\nhttps://example.com/live3.m3u")
        assertTrue(repository.getCachedLiveTvChannels().isEmpty())
    }

    @Test
    fun `getSavedLiveTvM3uUrl keeps multiple Admin Live TV URLs while filtering legacy broken entries`() {
        val adminInput = listOf(
            "https://example.com/Nafitv24.m3u",
            "https://example.com/bd_channels.m3u",
            "https://example.com/sports_tv.m3u;https://example.com/news_tv.m3u|User-Agent=CustomAgent",
            "https://example.com/Sports%20Channel%20NF.m3u"
        ).joinToString("\n")

        repository.saveLiveTvM3uUrl(adminInput)
        val extracted = repository.extractUrls(repository.getSavedLiveTvM3uUrl())

        assertTrue(extracted.any { it.contains("bd_channels.m3u") })
        assertTrue(extracted.any { it.contains("sports_tv.m3u") })
        assertTrue(extracted.any { it.contains("news_tv.m3u|User-Agent=CustomAgent") })
        assertFalse(extracted.any { it.contains("Nafitv24.m3u", ignoreCase = true) })
        assertFalse(extracted.any { it.contains("Sports%20Channel%20NF.m3u") })
    }

    @Test
    fun `mergeLiveTvChannelsFast preserves all unique channels and combines servers across Admin Live TV playlists`() {
        val chPl1 = MediaItem(
            id = "p1_sony",
            title = "Sony SAB HD",
            category = "Live TV",
            type = MediaType.LIVE_TV,
            streamUrl = "http://server1.example.com/sony.ts",
            servers = listOf(StreamServer("সার্ভার ১ (Main)", "http://server1.example.com/sony.ts")),
            isLive = true
        )
        val chPl2 = MediaItem(
            id = "p2_sony",
            title = "Sony SAB HD",
            category = "Family",
            type = MediaType.LIVE_TV,
            streamUrl = "https://server2.example.com/sony.m3u8",
            servers = listOf(StreamServer("সার্ভার ১ (Main)", "https://server2.example.com/sony.m3u8")),
            logoUrl = "https://img.example.com/sony_sab.png",
            isLive = true
        )
        val chUnique = MediaItem(
            id = "p2_yay",
            title = "Sony YAY",
            category = "Family",
            type = MediaType.LIVE_TV,
            streamUrl = "https://server2.example.com/yay.m3u8",
            isLive = true
        )

        val merged = repository.mergeLiveTvChannelsFast(listOf(chPl1, chPl2, chUnique))
        assertEquals(2, merged.size)

        val sony = merged.first { it.title == "Sony SAB HD" }
        assertEquals("Family", sony.category)
        assertEquals("https://img.example.com/sony_sab.png", sony.logoUrl)
        assertEquals(2, sony.servers.size)
        assertEquals("http://server1.example.com/sony.ts", sony.servers[0].url)
        assertEquals("https://server2.example.com/sony.m3u8", sony.servers[1].url)
    }
}

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
        repository = MediaRepository(context)
    }

    @Test
    fun `playlist classification excludes movie and sports json playlists from live tv`() {
        val toffeePl = PlaylistInfo(id = "pl_toffee_1", title = "Toffee 1", url = "https://example.com/toffee.m3u", type = "M3U")
        val moviePl = PlaylistInfo(id = "pl_ott_1", title = "NAFI OTT", url = "https://example.com/movies.m3u", type = "MOVIE", isMovieOnly = true)
        val fancodePl = PlaylistInfo(id = "pl_fancode", title = "FanCode", url = "https://example.com/fancode.json", type = "M3U")

        assertFalse(repository.isMovieOrVodOnlyPlaylist(toffeePl))
        assertFalse(repository.isSportsJsonOnlyPlaylist(toffeePl))
        assertTrue(repository.isMovieOrVodOnlyPlaylist(moviePl))
        assertTrue(repository.isSportsJsonOnlyPlaylist(fancodePl))
    }

    @Test
    fun `getInitialLiveTv includes cached channels from live playlists including drama live channels but excludes mp4 vod`() {
        val toffeePl = PlaylistInfo(
            id = "pl_toffee_1",
            title = "Toffee 1",
            url = "https://example.com/toffee.m3u",
            type = "M3U",
            isAdmin = true
        )
        repository.saveAdminPlaylist(toffeePl)

        val toffeeChannels = listOf(
            MediaItem(
                id = "t1",
                title = "Toffee TV",
                category = "Family",
                type = MediaType.LIVE_TV,
                streamUrl = "https://cdn.example.com/toffee_tv.m3u8",
                isLive = true
            ),
            MediaItem(
                id = "t2",
                title = "Sony SAB HD",
                category = "Family",
                type = MediaType.LIVE_TV,
                streamUrl = "https://cdn.example.com/sony_sab.m3u8",
                isLive = true
            ),
            MediaItem(
                id = "t3",
                title = "Sony YAY",
                category = "Family",
                type = MediaType.LIVE_TV,
                streamUrl = "https://cdn.example.com/sony_yay.ts",
                isLive = true
            ),
            MediaItem(
                id = "t4",
                title = "Zee Bangla Cinema TV",
                category = "Drama",
                type = MediaType.SERIES, // Even if previously cached as SERIES without VOD URL
                streamUrl = "https://bldcmprod-cdn.toffeelive.com/live/zee.m3u8",
                isLive = false
            ),
            MediaItem(
                id = "t5_vod",
                title = "Sample Movie MP4",
                category = "Movies",
                type = MediaType.MOVIE,
                streamUrl = "https://cdn.example.com/movie/sample.mp4",
                isLive = false
            )
        )
        repository.saveCachedPlaylistChannels(toffeePl.id, toffeeChannels)

        val initialLiveTv = repository.getInitialLiveTv()
        val titles = initialLiveTv.map { it.title }.toSet()

        assertTrue(titles.contains("Toffee TV"))
        assertTrue(titles.contains("Sony SAB HD"))
        assertTrue(titles.contains("Sony YAY"))
        assertTrue(titles.contains("Zee Bangla Cinema TV"))
        assertFalse(titles.contains("Sample Movie MP4"))

        val dramaLive = initialLiveTv.first { it.title == "Zee Bangla Cinema TV" }
        assertEquals(MediaType.LIVE_TV, dramaLive.type)
        assertTrue(dramaLive.isLive)
    }

    @Test
    fun `mergeLiveTvChannelsFast preserves all unique channels and combines servers across playlists`() {
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

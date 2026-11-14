package com.example

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.example.data.MediaRepository
import com.example.model.MediaItem
import com.example.model.MediaType
import com.example.model.PlaylistInfo
import com.example.ui.MoviesTabScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "w960dp-h540dp-land")
class MoviesTabTvLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `tv movies screen shows hero banner and poster row above fold even with selected hub`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = MediaRepository(context)
        val hubId = "pl_ott_test_nafi"
        repo.saveAdminPlaylist(
            PlaylistInfo(
                id = hubId,
                title = "NAFI OTT",
                url = "https://example.com/movies.m3u",
                type = "MOVIE",
                isAdmin = true,
                isMovieOnly = true
            )
        )
        repo.setLastSelectedMovieHubId(hubId)

        val sampleMovies = listOf(
            MediaItem(
                id = "m1",
                title = "Toofan 2026",
                category = "BANGLA",
                type = MediaType.MOVIE,
                streamUrl = "https://example.com/toofan.mp4",
                logoUrl = "https://example.com/toofan.jpg",
                rating = "8.9",
                year = "2026",
                isLive = false
            ),
            MediaItem(
                id = "m2",
                title = "Tandob 2026",
                category = "BANGLA",
                type = MediaType.MOVIE,
                streamUrl = "https://example.com/tandob.mp4",
                logoUrl = "https://example.com/tandob.jpg",
                rating = "8.7",
                year = "2026",
                isLive = false
            ),
            MediaItem(
                id = "m3",
                title = "Jawan",
                category = "Bollywood",
                type = MediaType.MOVIE,
                streamUrl = "https://example.com/jawan.mp4",
                logoUrl = "https://example.com/jawan.jpg",
                rating = "8.4",
                year = "2025",
                isLive = false
            )
        )
        repo.saveCachedPlaylistMovies(hubId, sampleMovies)

        composeTestRule.setContent {
            // Simulate TV space inside NafiTvMainApp (~455dp remaining below ~85dp top chrome)
            Box(modifier = Modifier.fillMaxSize()) {
                MoviesTabScreen(
                    movies = sampleMovies,
                    favoriteIds = emptySet(),
                    isLoading = false,
                    isTvMode = true,
                    isAdultHidden = true,
                    repository = repo,
                    onSelectMedia = {},
                    onToggleFavorite = {},
                    onOpenOfflineDownloads = {}
                )
            }
        }

        composeTestRule.waitForIdle()

        val heroNode = composeTestRule.onNodeWithTag("movies_hero_banner")
        heroNode.assertIsDisplayed()
        val heroBounds = heroNode.getBoundsInRoot()
        assertTrue(
            "Hero banner top (${heroBounds.top.value}dp) must sit in the upper half of TV screen so banners are immediately visible",
            heroBounds.top.value < 220f
        )

        val shelfHeader = composeTestRule.onNodeWithText("🎬 BANGLA")
        shelfHeader.assertIsDisplayed()
        val shelfBounds = shelfHeader.getBoundsInRoot()
        assertTrue(
            "First movie shelf header (${shelfBounds.top.value}dp) must be visible below hero banner on TV screen",
            shelfBounds.top.value < 420f
        )
    }
}

package com.example

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import com.example.data.MediaRepository
import com.example.model.MediaItem
import com.example.model.MediaType
import com.example.model.PlaylistInfo
import com.example.ui.MoviesTabScreen
import com.example.ui.MoviesTabSessionCache
import org.junit.Assert.assertTrue
import org.junit.Before
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

    @Before
    fun clearSessionCache() {
        MoviesTabSessionCache.clear()
    }

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

        // Wait for background Dispatchers.Default shelf preparation to complete
        composeTestRule.waitUntil(timeoutMillis = 5_000L) {
            composeTestRule.onAllNodesWithTag("movies_hero_banner").fetchSemanticsNodes().isNotEmpty() &&
                composeTestRule.onAllNodesWithText("🎬 BANGLA").fetchSemanticsNodes().isNotEmpty()
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

    @Test
    fun `movies stay loaded immediately after exiting video player without reloading`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = MediaRepository(context)
        val hubId = "pl_ott_session_persist"
        repo.saveAdminPlaylist(
            PlaylistInfo(
                id = hubId,
                title = "Chorki Hub",
                url = "https://example.com/chorki.m3u",
                type = "MOVIE",
                isAdmin = true,
                isMovieOnly = true
            )
        )
        repo.setLastSelectedMovieHubId(hubId)

        val loadedServerMovies = listOf(
            MediaItem(
                id = "srv_1",
                title = "Dear Life",
                category = "Chorki/Bangla",
                type = MediaType.SERIES,
                streamUrl = "https://example.com/dear_life.mp4",
                logoUrl = "https://example.com/dear_life.jpg",
                rating = "8.8",
                year = "2026",
                isLive = false
            ),
            MediaItem(
                id = "srv_2",
                title = "Feu (Bangla)",
                category = "Chorki/Bangla",
                type = MediaType.SERIES,
                streamUrl = "https://example.com/feu.mp4",
                logoUrl = "https://example.com/feu.jpg",
                rating = "8.5",
                year = "2026",
                isLive = false
            ),
            MediaItem(
                id = "srv_3",
                title = "Auto Shankar",
                category = "ZEE BANGLA",
                type = MediaType.SERIES,
                streamUrl = "https://example.com/autoshankar.mp4",
                logoUrl = "https://example.com/autoshankar.jpg",
                rating = "8.3",
                year = "2025",
                isLive = false
            )
        )
        repo.saveCachedPlaylistMovies(hubId, loadedServerMovies)

        var isPlayerOpen by mutableStateOf(false)

        composeTestRule.setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                if (!isPlayerOpen) {
                    MoviesTabScreen(
                        movies = emptyList(),
                        favoriteIds = emptySet(),
                        isLoading = false,
                        isTvMode = true,
                        isAdultHidden = true,
                        repository = repo,
                        onSelectMedia = { isPlayerOpen = true },
                        onToggleFavorite = {},
                        onOpenOfflineDownloads = {}
                    )
                }
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000L) {
            composeTestRule.onAllNodesWithText("Feu (Bangla)").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Feu (Bangla)").assertIsDisplayed()

        // Simulate opening VideoPlayerScreen (which removes MoviesTabScreen from composition)
        isPlayerOpen = true
        composeTestRule.waitForIdle()

        // Exit player back to MoviesTabScreen
        isPlayerOpen = false
        composeTestRule.waitForIdle()

        // Returning from player must immediately (Frame 0, no waitUntil) retain loaded server movies & shelves
        composeTestRule.onNodeWithText("Feu (Bangla)").assertIsDisplayed()
        composeTestRule.onNodeWithTag("movies_home_list").performScrollToNode(hasText("🎬 ZEE BANGLA"))
        composeTestRule.onNodeWithText("🎬 ZEE BANGLA").assertIsDisplayed()
    }
}

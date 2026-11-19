package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.LiveTv
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material.icons.rounded.TvOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.model.MediaItem
import com.example.ui.components.NafiLogoLoadingView
import com.example.ui.components.StickyLiveMiniPlayer
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.ChannelCardSurface
import com.example.ui.theme.CrimsonBright
import com.example.ui.theme.CrimsonGlow
import com.example.ui.theme.HindSiliguriFontFamily
import com.example.ui.theme.SlateNavyBackground
import com.example.util.ChannelStatusManager
import kotlinx.coroutines.delay

private val SERVER_CLEAN_REGEX = Regex("^(?i)(server|সার্ভার)\\s*\\d*.*")

fun mergeChannelsWithServers(channels: List<MediaItem>): List<MediaItem> {
    if (channels.isEmpty()) return emptyList()
    fun isDemoUrl(u: String): Boolean =
        u.contains("test-streams.mux.dev") ||
            u.contains("akamaized.net/hls/live/2000341/test") ||
            u.contains("bitdash-a.akamaihd.net") ||
            u.contains("bipbop") ||
            u.contains("bigbuckbunny")

    val seenIds = HashSet<String>(channels.size)
    val result = ArrayList<MediaItem>(channels.size)
    var index = 0
    for (item in channels) {
        val id = item.id.lowercase()
        if (id.startsWith("demo_") || id.contains("demo_sample")) continue
        val title = item.title.lowercase()
        if (title.contains("demo channel") || title.contains("ডেমো চ্যানেল") || title.contains("test stream") || title.contains("পরীক্ষামূলক টেস্ট")) continue
        val url = item.streamUrl.lowercase()
        if (isDemoUrl(url)) continue
        var isDemo = false
        for (s in item.servers) {
            if (isDemoUrl(s.url.lowercase())) {
                isDemo = true
                break
            }
        }
        if (isDemo) continue

        val safeServers = item.getAllServers()
        var uniqueId = item.id.ifBlank { "ch_${index}_${Math.abs(item.title.hashCode())}" }
        if (seenIds.contains(uniqueId)) {
            uniqueId = "${uniqueId}_${index}"
        }
        seenIds.add(uniqueId)
        result.add(item.copy(id = uniqueId, servers = safeServers))
        index++
    }
    return result
}

@Composable
fun LiveTvTabScreen(
    channels: List<MediaItem>,
    favoriteIds: Set<String>,
    isLoading: Boolean = false,
    isTvMode: Boolean = false,
    isAdultHidden: Boolean = true,
    repository: com.example.data.MediaRepository? = null,
    onSelectMedia: (MediaItem, List<MediaItem>) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onAddChannel: (MediaItem) -> Unit = {}
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf("ALL") }
    var showOnlyActive by rememberSaveable { mutableStateOf(false) }
    var stickySelectedChannelId by rememberSaveable { mutableStateOf<String?>(null) }

    val statusTick by ChannelStatusManager.statusUpdateTick.collectAsState()
    val firstChannelFocusRequester = remember { FocusRequester() }

    val shouldFilterAdult = isAdultHidden || (repository?.isAdultContentHidden() == true)
    val preparedChannels = remember(channels, shouldFilterAdult) {
        val merged = mergeChannelsWithServers(channels)
        if (shouldFilterAdult && repository != null) {
            merged.filterNot { repository.isMediaHidden(it) || repository.isAdultMedia(it) || repository.isAdultCategory(it.category) }
        } else if (shouldFilterAdult) {
            val adultWords = listOf(
                "adult", "adults", "18+", "18 +", "xxx", "nsfw", "erotic",
                "porn", "sex", "sensual", "mature", "brazzers", "playboy", "hustler", "for adults"
            )
            merged.filterNot { ch ->
                val txt = "${ch.title} ${ch.category} ${ch.genre ?: ""}".lowercase()
                adultWords.any { txt.contains(it) }
            }
        } else {
            merged
        }
    }

    val stickySelectedChannel = remember(stickySelectedChannelId, preparedChannels) {
        stickySelectedChannelId?.let { id -> preparedChannels.firstOrNull { it.id == id } }
    }

    val preparedCategories = remember(preparedChannels) {
        listOf("ALL", "FAVORITE") + preparedChannels.mapNotNull { it.category.takeIf { c -> c.isNotBlank() } }.distinct()
    }

    LaunchedEffect(preparedChannels, showOnlyActive) {
        if (showOnlyActive && preparedChannels.isNotEmpty()) {
            ChannelStatusManager.enqueueChannelsForProbing(preparedChannels)
        }
    }

    LaunchedEffect(showOnlyActive) {
        ChannelStatusManager.setOnlyActiveEnabled(showOnlyActive)
    }

    LaunchedEffect(isTvMode, preparedChannels.isNotEmpty()) {
        if (isTvMode && preparedChannels.isNotEmpty()) {
            delay(180)
            try {
                firstChannelFocusRequester.requestFocus()
            } catch (_: Exception) {}
        }
    }

    var debouncedSearchQuery by remember { mutableStateOf("") }
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            delay(140)
        }
        debouncedSearchQuery = searchQuery
    }

    val filteredChannels = remember(
        preparedChannels,
        debouncedSearchQuery,
        selectedCategory,
        favoriteIds,
        showOnlyActive,
        if (showOnlyActive) statusTick else 0L
    ) {
        preparedChannels.filter { channel ->
            val matchesSearch = debouncedSearchQuery.isBlank() ||
                channel.title.contains(debouncedSearchQuery, ignoreCase = true) ||
                channel.category.contains(debouncedSearchQuery, ignoreCase = true)
            val matchesCategory = when (selectedCategory) {
                "ALL" -> true
                "FAVORITE" -> favoriteIds.contains(channel.id)
                else -> channel.category.equals(selectedCategory, ignoreCase = true)
            }
            val matchesActive = if (showOnlyActive) ChannelStatusManager.isChannelActive(channel) else true
            matchesSearch && matchesCategory && matchesActive
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateNavyBackground)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        if (isLoading && preparedChannels.isNotEmpty()) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.5.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = AccentCyan,
                trackColor = Color(0xFF1E293B)
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        // Sticky Inline HLS / M3U8 / TS Video Player when a channel is selected on Mobile
        AnimatedVisibility(
            visible = !isTvMode && stickySelectedChannel != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            stickySelectedChannel?.let { activeChannel ->
                Column {
                    StickyLiveMiniPlayer(
                        channel = activeChannel,
                        onOpenFullscreen = { ch ->
                            // Release inline ExoPlayer before entering full-screen VideoPlayerScreen
                            stickySelectedChannelId = null
                            onSelectMedia(ch, filteredChannels.ifEmpty { preparedChannels })
                        },
                        onClose = { stickySelectedChannelId = null }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        // 2. Search (60%) & Only Active Channel Filter Row (40%)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Left Box (~60% width): Dark rounded search input with cyan search icon
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = ChannelCardSurface,
                border = BorderStroke(
                    width = 1.dp,
                    color = if (searchQuery.isNotEmpty()) AccentCyan.copy(alpha = 0.85f) else Color(0xFF1E293B)
                ),
                modifier = Modifier
                    .weight(0.58f)
                    .height(48.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search channels",
                        tint = AccentCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "লাইভ টিভি চ্যানেল খুঁজুন...",
                                color = Color(0xFF64748B),
                                fontFamily = HindSiliguriFontFamily,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        BasicTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.White,
                                fontFamily = HindSiliguriFontFamily,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            cursorBrush = SolidColor(AccentCyan),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("live_search_input")
                        )
                    }
                    if (searchQuery.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Clear search",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { searchQuery = "" }
                        )
                    }
                }
            }

            // Right Box (~40% width): Dark rounded "• Only Active Channel" toggle switch
            var isOnlyActiveFocused by remember { mutableStateOf(false) }
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (showOnlyActive) Color(0xFF064E3B).copy(alpha = 0.55f) else ChannelCardSurface,
                border = BorderStroke(
                    width = if (isTvMode && isOnlyActiveFocused) 2.dp else 1.dp,
                    color = when {
                        isTvMode && isOnlyActiveFocused -> CrimsonBright
                        showOnlyActive -> AccentEmerald
                        else -> Color(0xFF1E293B)
                    }
                ),
                modifier = Modifier
                    .weight(0.42f)
                    .height(48.dp)
                    .then(
                        if (isTvMode) {
                            Modifier
                                .onFocusChanged { isOnlyActiveFocused = it.isFocused }
                                .focusable()
                        } else {
                            Modifier
                        }
                    )
                    .clickable { showOnlyActive = !showOnlyActive }
                    .testTag("only_active_toggle")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 9.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(if (showOnlyActive) AccentEmerald else Color(0xFF64748B))
                        )
                        Text(
                            text = "Only Active Channel",
                            color = if (showOnlyActive) Color(0xFF6EE7B7) else Color(0xFFCBD5E1),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 12.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Switch(
                        checked = showOnlyActive,
                        onCheckedChange = { showOnlyActive = it },
                        modifier = Modifier.scale(0.68f),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AccentEmerald,
                            uncheckedThumbColor = Color(0xFF94A3B8),
                            uncheckedTrackColor = Color(0xFF1E293B),
                            uncheckedBorderColor = Color(0xFF334155)
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 3. Horizontal Scrollable Pill-Shaped Category Tabs
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(preparedCategories, key = { it }) { cat ->
                val isSelected = selectedCategory == cat
                var isCatFocused by remember { mutableStateOf(false) }
                val label = when (cat) {
                    "ALL" -> if (isLoading && preparedChannels.isEmpty()) "সকল চ্যানেল (লোড হচ্ছে...)" else "সকল চ্যানেল (${preparedChannels.size})"
                    "FAVORITE" -> "⭐ প্রিয় (${favoriteIds.size})"
                    else -> cat
                }
                Surface(
                    shape = RoundedCornerShape(50),
                    color = if (isSelected) AccentCyan else if (isTvMode && isCatFocused) Color(0xFF2B0E17) else Color(0xFF1E293B),
                    border = BorderStroke(
                        width = if (isTvMode && isCatFocused) 2.dp else 1.dp,
                        color = when {
                            isTvMode && isCatFocused -> CrimsonBright
                            isSelected -> Color(0xFF22D3EE)
                            else -> Color(0xFF334155).copy(alpha = 0.7f)
                        }
                    ),
                    shadowElevation = if (isSelected || isCatFocused) 6.dp else 0.dp,
                    modifier = Modifier
                        .then(
                            if (isTvMode) {
                                Modifier
                                    .onFocusChanged { isCatFocused = it.isFocused }
                                    .focusable()
                            } else {
                                Modifier
                            }
                        )
                        .clickable { selectedCategory = cat }
                        .testTag("category_tab_$cat")
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) SlateNavyBackground else Color.White,
                        fontFamily = HindSiliguriFontFamily,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 4. Interactive 3-Column Live TV Channel Grid & Selection Effect
        if (isLoading && preparedChannels.isEmpty()) {
            NafiLogoLoadingView(
                title = "লাইভ টিভি চ্যানেল লোড হচ্ছে...",
                subtitle = "অনুগ্রহ করে অপেক্ষা করুন, টিভি চ্যানেল ও সার্ভার প্রস্তুত হচ্ছে...",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        } else if (filteredChannels.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.TvOff,
                        contentDescription = null,
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "কোনো চ্যানেল পাওয়া যায়নি",
                        color = Color(0xFF94A3B8),
                        fontFamily = HindSiliguriFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            val gridState = rememberLazyGridState()
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(if (isTvMode) 4 else 3),
                contentPadding = PaddingValues(start = 2.dp, end = 2.dp, top = 4.dp, bottom = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(filteredChannels, key = { index, it -> "${it.id.ifBlank { "ch" }}_$index" }) { index, channel ->
                    val isFav = favoriteIds.contains(channel.id)
                    val isActive = ChannelStatusManager.isChannelActive(channel)
                    var isFocused by remember { mutableStateOf(false) }
                    val isSelected = !isTvMode && stickySelectedChannelId == channel.id
                    // Apply the exact same Dark-Crimson Liquid Glow effect on TV Mode D-pad focus & Mobile selection
                    val isCrimsonHighlighted = isSelected || (isTvMode && isFocused)

                    val animatedScale by animateFloatAsState(
                        targetValue = when {
                            isCrimsonHighlighted -> if (isTvMode) 1.055f else 1.045f
                            else -> 1f
                        },
                        animationSpec = spring(
                            dampingRatio = 0.65f,
                            stiffness = Spring.StiffnessMedium
                        ),
                        label = "channel_card_scale"
                    )

                    val starScale by animateFloatAsState(
                        targetValue = if (isFav) 1.18f else 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        ),
                        label = "fav_star_scale"
                    )

                    val cardHeight = if (isTvMode) 124.dp else 112.dp
                    val cardShape = RoundedCornerShape(14.dp)

                    val clickAction = {
                        if (isTvMode) {
                            onSelectMedia(channel, filteredChannels)
                        } else if (stickySelectedChannelId == channel.id) {
                            // Tapping the already-playing glowing card again opens full-screen VideoPlayerScreen
                            stickySelectedChannelId = null
                            onSelectMedia(channel, filteredChannels)
                        } else {
                            // Select channel with Dark-Crimson Liquid Glow and open/update sticky top player
                            stickySelectedChannelId = channel.id
                        }
                    }

                    val focusModifier = if (isTvMode) {
                        Modifier
                            .then(if (index == 0) Modifier.focusRequester(firstChannelFocusRequester) else Modifier)
                            .onFocusChanged { isFocused = it.isFocused }
                            .focusable()
                    } else {
                        Modifier
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(cardHeight)
                            .zIndex(if (isCrimsonHighlighted) 2f else 0f)
                            .scale(animatedScale)
                            .then(
                                if (isCrimsonHighlighted) {
                                    Modifier
                                        .shadow(
                                            elevation = 16.dp,
                                            shape = cardShape,
                                            ambientColor = CrimsonGlow,
                                            spotColor = CrimsonBright
                                        )
                                        .drawBehind {
                                            // Outer soft crimson liquid glow halo
                                            drawRoundRect(
                                                brush = Brush.radialGradient(
                                                    colors = listOf(
                                                        CrimsonBright.copy(alpha = 0.45f),
                                                        CrimsonGlow.copy(alpha = 0.24f),
                                                        Color.Transparent
                                                    ),
                                                    center = Offset(size.width / 2f, size.height / 2f),
                                                    radius = size.maxDimension * 0.88f
                                                ),
                                                cornerRadius = CornerRadius(18.dp.toPx(), 18.dp.toPx())
                                            )
                                        }
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        Card(
                            shape = cardShape,
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCrimsonHighlighted) Color(0xFF1D0D14) else ChannelCardSurface
                            ),
                            border = when {
                                isCrimsonHighlighted -> BorderStroke(
                                    width = 2.dp,
                                    brush = Brush.verticalGradient(
                                        listOf(CrimsonBright, CrimsonGlow, Color(0xFF8A021B))
                                    )
                                )
                                isActive -> BorderStroke(1.dp, Color(0xFF1E293B))
                                else -> BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.35f))
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .then(if (!isActive && !isCrimsonHighlighted) Modifier.alpha(0.75f) else Modifier)
                                .then(focusModifier)
                                .clickable(onClick = clickAction)
                                .testTag("channel_card_${channel.id}")
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .then(
                                        if (isCrimsonHighlighted) {
                                            Modifier.background(
                                                Brush.radialGradient(
                                                    colors = listOf(
                                                        CrimsonGlow.copy(alpha = 0.36f),
                                                        Color(0xFF3E0614).copy(alpha = 0.22f),
                                                        Color.Transparent
                                                    ),
                                                    radius = 220f
                                                )
                                            )
                                        } else {
                                            Modifier
                                        }
                                    )
                            ) {
                                // Main Channel Logo & Title Stack
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(start = 6.dp, end = 6.dp, top = 22.dp, bottom = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(if (isTvMode) 50.dp else 46.dp)
                                            .clip(CircleShape)
                                            .background(if (isCrimsonHighlighted) Color(0xFF14090E) else SlateNavyBackground),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (!channel.logoUrl.isNullOrBlank()) {
                                            AsyncImage(
                                                model = channel.logoUrl,
                                                contentDescription = channel.title,
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                                    .size(if (isTvMode) 38.dp else 34.dp)
                                                    .clip(RoundedCornerShape(6.dp))
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Rounded.LiveTv,
                                                contentDescription = null,
                                                tint = if (isCrimsonHighlighted) CrimsonBright else AccentCyan,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = channel.title,
                                        color = Color.White,
                                        fontFamily = HindSiliguriFontFamily,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                // Top-Left Corner: Crimson "• LIVE" when highlighted/selected, else dark-green "• সচল" or offline badge
                                if (isCrimsonHighlighted) {
                                    Surface(
                                        shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 8.dp),
                                        color = CrimsonGlow,
                                        modifier = Modifier.align(Alignment.TopStart)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(5.dp)
                                                    .clip(CircleShape)
                                                    .background(Color.White)
                                            )
                                            Text(
                                                text = "LIVE",
                                                color = Color.White,
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                        }
                                    }
                                } else if (isActive) {
                                    Surface(
                                        shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 8.dp),
                                        color = Color(0xFF064E3B).copy(alpha = 0.92f),
                                        border = BorderStroke(0.75.dp, AccentEmerald.copy(alpha = 0.7f)),
                                        modifier = Modifier.align(Alignment.TopStart)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(5.dp)
                                                    .clip(CircleShape)
                                                    .background(AccentEmerald)
                                            )
                                            Text(
                                                text = "সচল",
                                                color = Color(0xFF6EE7B7),
                                                fontFamily = HindSiliguriFontFamily,
                                                fontSize = 8.5.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                } else {
                                    Surface(
                                        shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 8.dp),
                                        color = Color(0xFF450A0A).copy(alpha = 0.95f),
                                        border = BorderStroke(0.75.dp, Color(0xFFEF4444).copy(alpha = 0.6f)),
                                        modifier = Modifier.align(Alignment.TopStart)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(5.dp)
                                                    .clip(CircleShape)
                                                    .background(Color(0xFFEF4444))
                                            )
                                            Text(
                                                text = "অফলাইন",
                                                color = Color(0xFFFCA5A5),
                                                fontFamily = HindSiliguriFontFamily,
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                // Top-Right Corner: Interactive Favorite Star
                                IconButton(
                                    onClick = { onToggleFavorite(channel.id) },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(30.dp)
                                        .testTag("fav_star_${channel.id}")
                                ) {
                                    Icon(
                                        imageVector = if (isFav) Icons.Rounded.Star else Icons.Rounded.StarBorder,
                                        contentDescription = if (isFav) "Remove from favorites" else "Add to favorites",
                                        tint = if (isFav) AccentAmber else Color(0xFF64748B),
                                        modifier = Modifier
                                            .size(16.dp)
                                            .scale(starScale)
                                    )
                                }

                                // Subtle crimson liquid bottom rim accent on selected/focused card
                                if (isCrimsonHighlighted) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .fillMaxWidth(0.55f)
                                            .height(2.5.dp)
                                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                            .background(
                                                Brush.horizontalGradient(
                                                    listOf(
                                                        Color.Transparent,
                                                        CrimsonBright,
                                                        Color.Transparent
                                                    )
                                                )
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

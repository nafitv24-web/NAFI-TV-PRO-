package com.example.player

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.MediaItem as AppMediaItem

@Composable
fun VideoPlayerQuickDrawer(
    currentMedia: AppMediaItem,
    playlist: List<AppMediaItem>,
    onSelectChannel: (AppMediaItem) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasSeriesEpisodes = (currentMedia.isSeries || currentMedia.episodes.isNotEmpty()) && currentMedia.episodes.isNotEmpty()
    var drawerSearchQuery by remember { mutableStateOf("") }
    var drawerSelectedCategory by remember { mutableStateOf("All") }
    var drawerTab by remember(currentMedia.id) {
        mutableStateOf(if (hasSeriesEpisodes) "EPISODES" else "CHANNELS")
    }
    var drawerSeasonNum by remember(currentMedia.id) {
        mutableIntStateOf(if (currentMedia.currentSeasonNum > 0) currentMedia.currentSeasonNum else 1)
    }

    val drawerCategories = remember(playlist) {
        val cats = playlist.map { it.category.trim() }
            .filter { it.isNotBlank() && !it.equals("Unknown", ignoreCase = true) }
            .distinct()
            .sorted()
        listOf("All") + cats
    }

    val filteredDrawerPlaylist = remember(playlist, drawerSearchQuery, drawerSelectedCategory) {
        playlist.filter { item ->
            val matchesSearch = if (drawerSearchQuery.isBlank()) true else {
                item.title.contains(drawerSearchQuery, ignoreCase = true) ||
                item.category.contains(drawerSearchQuery, ignoreCase = true)
            }
            val matchesCat = if (drawerSelectedCategory == "All") true else {
                item.category.trim().equals(drawerSelectedCategory.trim(), ignoreCase = true)
            }
            matchesSearch && matchesCat
        }
    }

    // Filtered Series Episodes for Current Season
    val filteredEpisodes = remember(currentMedia.episodes, drawerSeasonNum, drawerSearchQuery) {
        currentMedia.episodes.filter { ep ->
            val matchesSeason = ep.seasonNum == drawerSeasonNum || currentMedia.seasons.isEmpty()
            val matchesSearch = if (drawerSearchQuery.isBlank()) true else {
                ep.title.contains(drawerSearchQuery, ignoreCase = true) ||
                "পর্ব ${ep.episodeNum}".contains(drawerSearchQuery, ignoreCase = true) ||
                "${ep.episodeNum}".contains(drawerSearchQuery)
            }
            matchesSeason && matchesSearch
        }
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp)
            .background(Color(0xFF0F172A).copy(alpha = 0.98f))
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (drawerTab == "EPISODES") "📺 পর্বসমূহ (${currentMedia.episodes.size})" else "📺 চ্যানেল তালিকা (${filteredDrawerPlaylist.size})",
                    color = Color(0xFF00E5FF),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(Icons.Rounded.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }

            // Tab Switch if both Series Episodes and Playlist are available
            if (hasSeriesEpisodes && playlist.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (drawerTab == "EPISODES") Color(0xFF0284C7) else Color(0xFF1E293B),
                        border = BorderStroke(1.dp, if (drawerTab == "EPISODES") Color(0xFF00E5FF) else Color(0xFF334155)),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { drawerTab = "EPISODES" }
                    ) {
                        Text(
                            text = "📺 পর্বসমূহ",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 6.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (drawerTab == "CHANNELS") Color(0xFF0284C7) else Color(0xFF1E293B),
                        border = BorderStroke(1.dp, if (drawerTab == "CHANNELS") Color(0xFF00E5FF) else Color(0xFF334155)),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { drawerTab = "CHANNELS" }
                    ) {
                        Text(
                            text = "📡 চ্যানেল/মুভি",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 6.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Search Box inside Drawer
            OutlinedTextField(
                value = drawerSearchQuery,
                onValueChange = { drawerSearchQuery = it },
                placeholder = { Text(if (drawerTab == "EPISODES") "পর্ব খুঁজুন (যেমন: ১, ২)..." else "চ্যানেল খুঁজুন...", color = Color(0xFF94A3B8), fontSize = 11.sp) },
                leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null, tint = Color(0xFF00E5FF), modifier = Modifier.size(16.dp)) },
                trailingIcon = {
                    if (drawerSearchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { drawerSearchQuery = "" },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(Icons.Rounded.Close, contentDescription = "Clear", tint = Color.White, modifier = Modifier.size(13.dp))
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00E5FF),
                    unfocusedBorderColor = Color(0xFF334155),
                    focusedContainerColor = Color(0xFF1E293B),
                    unfocusedContainerColor = Color(0xFF1E293B),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF00E5FF)
                ),
                singleLine = true
            )

            if (drawerTab == "EPISODES") {
                // Season Chips for Series
                if (currentMedia.seasons.size > 1) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        items(currentMedia.seasons) { season ->
                            val isSelected = drawerSeasonNum == season.seasonNumber
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) Color(0xFF0284C7) else Color(0xFF1E293B),
                                border = BorderStroke(1.dp, if (isSelected) Color(0xFF00E5FF) else Color(0xFF334155)),
                                modifier = Modifier.clickable { drawerSeasonNum = season.seasonNumber }
                            ) {
                                Text(
                                    text = season.name.ifBlank { "সিজন ${season.seasonNumber}" },
                                    color = Color.White,
                                    fontSize = 10.5.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                // Episode List in Drawer
                if (filteredEpisodes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "কোনো পর্ব পাওয়া যায়নি",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.5.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredEpisodes, key = { it.id }) { ep ->
                            val isCurrent = ep.id == currentMedia.id || (ep.seasonNum == currentMedia.currentSeasonNum && ep.episodeNum == currentMedia.currentEpisodeNum)
                            var isFocused by remember { mutableStateOf(false) }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .scale(if (isFocused) 1.02f else 1.0f)
                                    .onFocusChanged { isFocused = it.isFocused }
                                    .focusable()
                                    .clickable {
                                        val epMedia = ep.toMediaItem(currentMedia)
                                        onSelectChannel(epMedia)
                                        onClose()
                                    },
                                shape = RoundedCornerShape(8.dp),
                                border = when {
                                    isFocused -> BorderStroke(2.dp, Color(0xFFFFD600))
                                    isCurrent -> BorderStroke(1.5.dp, Color(0xFF00E5FF))
                                    else -> BorderStroke(1.dp, Color(0xFF223456))
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = when {
                                        isFocused -> Color(0xFF1E3A8A)
                                        isCurrent -> Color(0xFF0284C7).copy(alpha = 0.35f)
                                        else -> Color(0xFF141F38)
                                    }
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(width = 50.dp, height = 34.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color(0xFF0F172A)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = if (isCurrent) Icons.Rounded.PlayArrow else Icons.Rounded.PlayCircle,
                                            contentDescription = null,
                                            tint = if (isCurrent) Color(0xFF00E5FF) else Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "পর্ব ${ep.episodeNum}: ${ep.title.ifBlank { "Episode ${ep.episodeNum}" }}",
                                                color = if (isFocused) Color(0xFFFFD600) else if (isCurrent) Color(0xFF00E5FF) else Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.5.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.weight(1f, fill = false)
                                            )
                                            if (isCurrent) {
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "▶ চলছে",
                                                    color = Color(0xFF00E5FF),
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontSize = 9.sp
                                                )
                                            }
                                        }
                                        if (!ep.duration.isNullOrBlank()) {
                                            Text(
                                                text = "সময়: ${ep.duration}",
                                                color = Color(0xFF94A3B8),
                                                fontSize = 9.5.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Category Filter Chips
                if (drawerCategories.size > 1) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        items(drawerCategories) { cat ->
                            val isSelected = drawerSelectedCategory == cat
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) Color(0xFF0284C7) else Color(0xFF1E293B),
                                border = BorderStroke(1.dp, if (isSelected) Color(0xFF00E5FF) else Color(0xFF334155)),
                                modifier = Modifier.clickable { drawerSelectedCategory = cat }
                            ) {
                                Text(
                                    text = if (cat == "All") "সকল" else cat,
                                    color = Color.White,
                                    fontSize = 10.5.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                // Channel List in Drawer
                if (filteredDrawerPlaylist.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "কোনো চ্যানেল পাওয়া যায়নি",
                            color = Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredDrawerPlaylist, key = { it.id }) { item ->
                            val isCurrent = item.id == currentMedia.id
                            var isFocused by remember { mutableStateOf(false) }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .scale(if (isFocused) 1.02f else 1.0f)
                                    .onFocusChanged { isFocused = it.isFocused }
                                    .focusable()
                                    .clickable {
                                        onSelectChannel(item)
                                        onClose()
                                    },
                                shape = RoundedCornerShape(8.dp),
                                border = when {
                                    isFocused -> BorderStroke(2.dp, Color(0xFFFFD600))
                                    isCurrent -> BorderStroke(1.5.dp, Color(0xFF00E5FF))
                                    else -> BorderStroke(1.dp, Color(0xFF223456))
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = when {
                                        isFocused -> Color(0xFF1E3A8A)
                                        isCurrent -> Color(0xFF0284C7).copy(alpha = 0.35f)
                                        else -> Color(0xFF141F38)
                                    }
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = item.logoUrl ?: "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=100",
                                        contentDescription = item.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = item.title,
                                                color = if (isFocused || isCurrent) Color(0xFF00E5FF) else Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.5.sp,
                                                maxLines = 1,
                                                modifier = Modifier.weight(1f, fill = false)
                                            )
                                            if (isCurrent) {
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "▶ PLAYING",
                                                    color = Color(0xFF00E5FF),
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontSize = 8.5.sp
                                                )
                                            }
                                        }
                                        Text(
                                            text = item.category,
                                            color = if (isFocused) Color.White.copy(alpha = 0.9f) else Color(0xFF94A3B8),
                                            fontSize = 9.5.sp
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
}

package com.example.ui.components

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.FullscreenExit
import androidx.compose.material.icons.rounded.HighQuality
import androidx.compose.material.icons.rounded.LiveTv
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.VolumeOff
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem as ExoMediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.extractor.ts.DefaultTsPayloadReaderFactory
import androidx.media3.extractor.ts.TsExtractor
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.model.MediaItem
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.ChannelCardSurface
import com.example.ui.theme.CrimsonBright
import com.example.ui.theme.CrimsonGlow
import com.example.ui.theme.SlateNavyBackground
import com.example.util.DrmHelper
import com.example.util.MediaStreamingClient
import com.example.util.StreamKind
import com.example.util.StreamPlaybackSupport

/**
 * Sticky inline HLS / M3U8 / MPEG-TS Live TV Player rendered at the top of LiveTvTabScreen
 * when a channel is selected, featuring channel name, pulsing LIVE badge, server/quality selector,
 * minimize/expand toggle, 1-tap fullscreen launch, and close button.
 */
@OptIn(UnstableApi::class)
@Composable
fun StickyLiveMiniPlayer(
    channel: MediaItem,
    onOpenFullscreen: (MediaItem) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val servers = remember(channel.id, channel.servers) { channel.getAllServers() }
    var activeServerIndex by remember(channel.id) { mutableIntStateOf(0) }
    var isMinimized by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(true) }
    var isBuffering by remember(channel.id) { mutableStateOf(true) }
    var isMuted by remember { mutableStateOf(false) }
    var selectedQualityLabel by remember(channel.id) { mutableStateOf("Auto") }
    var showQualityMenu by remember { mutableStateOf(false) }
    var showServerMenu by remember { mutableStateOf(false) }
    var playbackError by remember(channel.id, activeServerIndex) { mutableStateOf<String?>(null) }

    val activeUrl = servers.getOrNull(activeServerIndex)?.url ?: channel.streamUrl

    val trackSelector = remember { DefaultTrackSelector(context) }
    val exoPlayer = remember(context) {
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(2_500, 20_000, 1_200, 2_000)
            .build()
        ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .setLoadControl(loadControl)
            .build()
            .apply {
                playWhenReady = true
            }
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isBuffering = state == Player.STATE_BUFFERING
                if (state == Player.STATE_READY) {
                    playbackError = null
                }
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }

            override fun onPlayerError(error: PlaybackException) {
                isBuffering = false
                if (activeServerIndex + 1 < servers.size) {
                    activeServerIndex += 1
                } else {
                    playbackError = "স্ট্রিম লোড হতে সমস্যা হয়েছে — ফুলস্ক্রিন প্লেয়ার চালু করুন"
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    LaunchedEffect(channel.id, activeUrl, selectedQualityLabel) {
        isBuffering = true
        playbackError = null
        try {
            val maxHeight = when (selectedQualityLabel) {
                "1080p" -> 1080
                "720p" -> 720
                "480p" -> 480
                "360p" -> 360
                else -> Int.MAX_VALUE
            }
            trackSelector.setParameters(
                trackSelector.buildUponParameters()
                    .setMaxVideoSize(Int.MAX_VALUE, maxHeight)
            )

            val info = DrmHelper.extractStreamInfo(
                rawUrl = activeUrl,
                itemScheme = channel.drmScheme,
                itemLicenseUrl = channel.drmLicenseUrl,
                itemLicenseKey = channel.drmLicenseKey,
                itemManifestType = channel.manifestType
            )
            val cleanUrl = StreamPlaybackSupport.normalizeStreamUrl(info.cleanUrl)
            val headers = LinkedHashMap<String, String>()
            channel.customHeaders?.let { headers.putAll(it) }
            headers.putAll(info.headers)
            channel.referrer?.takeIf { it.isNotBlank() }?.let { headers.putIfAbsent("Referer", it) }
            channel.origin?.takeIf { it.isNotBlank() }?.let { headers.putIfAbsent("Origin", it) }
            channel.cookie?.takeIf { it.isNotBlank() }?.let { headers.putIfAbsent("Cookie", it) }
            StreamPlaybackSupport.extractBasicAuthHeader(cleanUrl)?.let { headers.putIfAbsent("Authorization", it) }

            val ua = channel.userAgent?.takeIf { it.isNotBlank() }
                ?: headers["User-Agent"]
                ?: MediaStreamingClient.USER_AGENT_TELEVIZO

            val classification = StreamPlaybackSupport.classifyStream(
                cleanUrl = cleanUrl,
                mediaType = channel.type,
                isLiveFlag = channel.isLive,
                isSeriesFlag = channel.isSeries,
                category = channel.category,
                drmManifestType = info.drmConfig?.manifestType,
                itemManifestType = channel.manifestType
            )

            val (_, dataSourceFactory) = MediaStreamingClient.createDataSourceFactory(
                context = context,
                userAgent = ua,
                headers = headers,
                enableTsSyncAlignment = classification.isTsStream || classification.isXtreamStream
            )

            val mediaItemBuilder = ExoMediaItem.Builder().setUri(Uri.parse(cleanUrl))
            classification.exoMimeType?.let { mediaItemBuilder.setMimeType(it) }
            val exoItem = mediaItemBuilder.build()

            val tsFlags = DefaultTsPayloadReaderFactory.FLAG_ALLOW_NON_IDR_KEYFRAMES or
                DefaultTsPayloadReaderFactory.FLAG_DETECT_ACCESS_UNITS
            val extractorsFactory = DefaultExtractorsFactory()
                .setTsExtractorFlags(tsFlags)
                .setTsExtractorTimestampSearchBytes(TsExtractor.DEFAULT_TIMESTAMP_SEARCH_BYTES * 6)
                .setAdtsExtractorFlags(androidx.media3.extractor.ts.AdtsExtractor.FLAG_ENABLE_CONSTANT_BITRATE_SEEKING)

            val mediaSource = when (classification.kind) {
                StreamKind.HLS -> HlsMediaSource.Factory(dataSourceFactory)
                    .setAllowChunklessPreparation(true)
                    .createMediaSource(exoItem)
                StreamKind.DASH -> DashMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(exoItem)
                StreamKind.MPEG_TS, StreamKind.PROGRESSIVE ->
                    ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory)
                        .createMediaSource(exoItem)
                else -> DefaultMediaSourceFactory(dataSourceFactory, extractorsFactory)
                    .createMediaSource(exoItem)
            }

            exoPlayer.setMediaSource(mediaSource)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            exoPlayer.volume = if (isMuted) 0f else 1f
        } catch (_: Throwable) {
            isBuffering = false
            playbackError = "ইনলাইন প্লেয়ারে চালানো যায়নি — ফুলস্ক্রিনে দেখুন"
        }
    }

    val pulseTransition = rememberInfiniteTransition(label = "live_dot_pulse")
    val liveDotAlpha by pulseTransition.animateFloat(
        initialValue = 0.45f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(700), RepeatMode.Reverse),
        label = "live_dot_alpha"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .testTag("sticky_live_player"),
        shape = RoundedCornerShape(16.dp),
        color = ChannelCardSurface,
        border = BorderStroke(1.5.dp, CrimsonGlow.copy(alpha = 0.85f)),
        shadowElevation = 10.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Sticky Header Bar: Channel Logo + Name + LIVE badge + Quality + Server + Minimize + Fullscreen + Close
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF2B0712),
                                ChannelCardSurface,
                                SlateNavyBackground
                            )
                        )
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Channel Logo
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(SlateNavyBackground),
                    contentAlignment = Alignment.Center
                ) {
                    if (!channel.logoUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = channel.logoUrl,
                            contentDescription = channel.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.LiveTv,
                            contentDescription = null,
                            tint = AccentCyan,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Channel Name & Pulsing LIVE Badge
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = channel.title,
                            color = Color.White,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = CrimsonGlow
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .alpha(liveDotAlpha)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                                Text(
                                    text = "LIVE",
                                    color = Color.White,
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }
                    Text(
                        text = "${channel.category} • $selectedQualityLabel",
                        color = Color(0xFF94A3B8),
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Quality Selector Chip
                Box {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF1E293B),
                        border = BorderStroke(1.dp, AccentCyan.copy(alpha = 0.45f)),
                        modifier = Modifier
                            .clickable { showQualityMenu = true }
                            .testTag("sticky_player_quality_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.HighQuality,
                                contentDescription = "Quality",
                                tint = AccentCyan,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = selectedQualityLabel,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = showQualityMenu,
                        onDismissRequest = { showQualityMenu = false },
                        containerColor = Color(0xFF1E293B)
                    ) {
                        listOf("Auto", "1080p", "720p", "480p", "360p").forEach { q ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = if (q == selectedQualityLabel) "✓ $q" else q,
                                        color = if (q == selectedQualityLabel) AccentCyan else Color.White,
                                        fontWeight = if (q == selectedQualityLabel) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                },
                                onClick = {
                                    selectedQualityLabel = q
                                    showQualityMenu = false
                                }
                            )
                        }
                    }
                }

                // Server Switcher if multiple servers exist
                if (servers.size > 1) {
                    Box {
                        IconButton(
                            onClick = { showServerMenu = true },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Dns,
                                contentDescription = "Switch Server",
                                tint = Color(0xFFF59E0B),
                                modifier = Modifier.size(17.dp)
                            )
                        }
                        DropdownMenu(
                            expanded = showServerMenu,
                            onDismissRequest = { showServerMenu = false },
                            containerColor = Color(0xFF1E293B)
                        ) {
                            servers.forEachIndexed { idx, srv ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = if (idx == activeServerIndex) "✓ ${srv.name}" else srv.name,
                                            color = if (idx == activeServerIndex) AccentCyan else Color.White,
                                            fontSize = 12.sp
                                        )
                                    },
                                    onClick = {
                                        activeServerIndex = idx
                                        showServerMenu = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Minimize / Expand Toggle
                IconButton(
                    onClick = { isMinimized = !isMinimized },
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("sticky_player_minimize_button")
                ) {
                    Icon(
                        imageVector = if (isMinimized) Icons.Rounded.PlayArrow else Icons.Rounded.FullscreenExit,
                        contentDescription = if (isMinimized) "Expand Player" else "Minimize Player",
                        tint = Color(0xFFCBD5E1),
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Fullscreen Launch Button
                IconButton(
                    onClick = { onOpenFullscreen(channel) },
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("sticky_player_fullscreen_button")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Fullscreen,
                        contentDescription = "Fullscreen Player",
                        tint = AccentCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Close Button
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("sticky_player_close_button")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close Player",
                        tint = Color(0xFFFCA5A5),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Collapsible 16:9 Live Video Viewport
            AnimatedVisibility(
                visible = !isMinimized,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .background(Color.Black)
                ) {
                    AndroidView(
                        factory = { ctx ->
                            PlayerView(ctx).apply {
                                player = exoPlayer
                                useController = false
                                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                                layoutParams = FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                            }
                        },
                        update = { view ->
                            if (view.player !== exoPlayer) {
                                view.player = exoPlayer
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    if (isBuffering && playbackError == null) {
                        CircularProgressIndicator(
                            color = CrimsonBright,
                            strokeWidth = 2.5.dp,
                            modifier = Modifier
                                .size(34.dp)
                                .align(Alignment.Center)
                        )
                    }

                    if (playbackError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.72f))
                                .clickable { onOpenFullscreen(channel) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = playbackError.orEmpty(),
                                    color = Color(0xFFFECACA),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = CrimsonGlow
                                ) {
                                    Text(
                                        text = "ফুলস্ক্রিন প্লেয়ারে চালু করুন",
                                        color = Color.White,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Bottom Mini Overlay Controls (Play/Pause, Mute, Fullscreen)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.6f),
                            modifier = Modifier
                                .size(34.dp)
                                .clickable {
                                    if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                                    contentDescription = if (isPlaying) "Pause" else "Play",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.6f),
                            modifier = Modifier
                                .size(34.dp)
                                .clickable {
                                    isMuted = !isMuted
                                    exoPlayer.volume = if (isMuted) 0f else 1f
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isMuted) Icons.Rounded.VolumeOff else Icons.Rounded.VolumeUp,
                                    contentDescription = if (isMuted) "Unmute" else "Mute",
                                    tint = Color.White,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

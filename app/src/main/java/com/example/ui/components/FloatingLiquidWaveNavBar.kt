package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppTab
import com.example.ui.theme.CrimsonBright
import com.example.ui.theme.CrimsonGlow
import com.example.ui.theme.FloatingNavSurface
import com.example.ui.theme.InterFontFamily

/**
 * Floating Matte-Black Pill Bottom Navigation Bar (#141416) with a glowing crimson-red
 * bottom rim (#D90429) and an animated horizontal liquid-wave hill bulge tracking the active tab.
 */
@Composable
fun FloatingLiquidWaveNavBar(
    currentTab: AppTab,
    onSelectTab: (AppTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = remember { AppTab.values().toList() }
    val selectedIndex = tabs.indexOf(currentTab).coerceAtLeast(0)

    val animatedIndex by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = spring(
            dampingRatio = 0.72f,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "liquid_wave_tab_index"
    )

    val pillShape = RoundedCornerShape(28.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .shadow(
                    elevation = 18.dp,
                    shape = pillShape,
                    ambientColor = CrimsonGlow.copy(alpha = 0.45f),
                    spotColor = CrimsonGlow.copy(alpha = 0.55f)
                )
                .clip(pillShape)
        ) {
            // Custom Canvas: Matte-black pill + crimson radial glow + gliding liquid wave bulge + glowing bottom rim
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val cornerPx = 28.dp.toPx()
                val tabWidth = w / tabs.size.toFloat()
                val activeCenterX = tabWidth * (animatedIndex + 0.5f)

                val pillPath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(0f, 0f, w, h),
                            cornerRadius = CornerRadius(cornerPx, cornerPx)
                        )
                    )
                }

                clipPath(pillPath) {
                    // 1. Matte-black pill base (#141416)
                    drawRect(color = FloatingNavSurface)

                    // 2. Subtle top sheen for matte depth
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.06f),
                                Color.Transparent
                            ),
                            startY = 0f,
                            endY = h * 0.38f
                        )
                    )

                    // 3. Deep dark-crimson radial glow radiating up from the active liquid wave
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                CrimsonBright.copy(alpha = 0.44f),
                                CrimsonGlow.copy(alpha = 0.24f),
                                Color(0xFF4A000E).copy(alpha = 0.10f),
                                Color.Transparent
                            ),
                            center = Offset(activeCenterX, h * 0.88f),
                            radius = tabWidth * 1.15f
                        ),
                        center = Offset(activeCenterX, h * 0.88f),
                        radius = tabWidth * 1.15f
                    )

                    // 4. Smooth upward crimson-red liquid wave / hill bulge along the bottom border
                    val waveHalfSpan = (tabWidth * 0.72f).coerceAtLeast(38.dp.toPx())
                    val baseRimY = h - 3.dp.toPx()
                    val peakY = h - 16.dp.toPx()
                    val leftX = activeCenterX - waveHalfSpan
                    val rightX = activeCenterX + waveHalfSpan

                    val waveFillPath = Path().apply {
                        moveTo(0f, h)
                        lineTo(0f, baseRimY)
                        lineTo(leftX.coerceAtLeast(0f), baseRimY)
                        cubicTo(
                            activeCenterX - waveHalfSpan * 0.46f, baseRimY,
                            activeCenterX - waveHalfSpan * 0.34f, peakY,
                            activeCenterX, peakY
                        )
                        cubicTo(
                            activeCenterX + waveHalfSpan * 0.34f, peakY,
                            activeCenterX + waveHalfSpan * 0.46f, baseRimY,
                            rightX.coerceAtMost(w), baseRimY
                        )
                        lineTo(w, baseRimY)
                        lineTo(w, h)
                        close()
                    }

                    drawPath(
                        path = waveFillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                CrimsonBright.copy(alpha = 0.95f),
                                CrimsonGlow.copy(alpha = 0.90f),
                                Color(0xFF7A0016)
                            ),
                            startY = peakY,
                            endY = h
                        )
                    )

                    // 5. Soft glowing rim stroke tracing the bottom edge and rising smoothly over the liquid crest
                    val waveStrokePath = Path().apply {
                        moveTo(cornerPx * 0.35f, baseRimY)
                        lineTo(leftX.coerceAtLeast(cornerPx * 0.35f), baseRimY)
                        cubicTo(
                            activeCenterX - waveHalfSpan * 0.46f, baseRimY,
                            activeCenterX - waveHalfSpan * 0.34f, peakY,
                            activeCenterX, peakY
                        )
                        cubicTo(
                            activeCenterX + waveHalfSpan * 0.34f, peakY,
                            activeCenterX + waveHalfSpan * 0.46f, baseRimY,
                            rightX.coerceAtMost(w - cornerPx * 0.35f), baseRimY
                        )
                        lineTo(w - cornerPx * 0.35f, baseRimY)
                    }

                    // Outer soft bloom on the crimson rim
                    drawPath(
                        path = waveStrokePath,
                        color = CrimsonBright.copy(alpha = 0.45f),
                        style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
                    )
                    // Crisp inner crimson-glow highlight line
                    drawPath(
                        path = waveStrokePath,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                CrimsonGlow.copy(alpha = 0.55f),
                                Color(0xFFFF4D6D),
                                CrimsonGlow.copy(alpha = 0.55f)
                            )
                        ),
                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                // 6. Subtle outer pill perimeter stroke (faint glass top, crimson bottom tint)
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.12f),
                            CrimsonGlow.copy(alpha = 0.25f),
                            CrimsonGlow.copy(alpha = 0.85f)
                        )
                    ),
                    cornerRadius = CornerRadius(cornerPx, cornerPx),
                    style = Stroke(width = 1.2.dp.toPx())
                )
            }

            // 5 Interactive Tab Buttons
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEach { tab ->
                    val isSelected = tab == currentTab
                    val iconTint by animateColorAsState(
                        targetValue = if (isSelected) Color(0xFFFF2E54) else Color(0xFF94A3B8),
                        animationSpec = tween(220),
                        label = "tab_icon_tint_${tab.name}"
                    )
                    val labelTint by animateColorAsState(
                        targetValue = if (isSelected) Color(0xFFFF4D6D) else Color(0xFF94A3B8),
                        animationSpec = tween(220),
                        label = "tab_label_tint_${tab.name}"
                    )
                    val iconFloatY by animateDpAsState(
                        targetValue = if (isSelected) (-3).dp else 0.dp,
                        animationSpec = spring(dampingRatio = 0.65f, stiffness = Spring.StiffnessMedium),
                        label = "tab_float_${tab.name}"
                    )
                    val interactionSource = remember { MutableInteractionSource() }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { onSelectTab(tab) }
                            .padding(top = 6.dp, bottom = 10.dp)
                            .testTag("nav_tab_${tab.name.lowercase()}"),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = when (tab) {
                                AppTab.LIVE_TV -> Icons.Rounded.Tv
                                AppTab.EVENTS -> Icons.Rounded.EmojiEvents
                                AppTab.MOVIES -> Icons.Rounded.Movie
                                AppTab.PLAYLIST -> Icons.Rounded.Folder
                                AppTab.MENU -> Icons.Rounded.Menu
                            },
                            contentDescription = tab.englishLabel,
                            tint = iconTint,
                            modifier = Modifier
                                .size(if (isSelected) 23.dp else 21.dp)
                                .offset(y = iconFloatY)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = tab.englishLabel,
                            color = labelTint,
                            fontFamily = InterFontFamily,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.offset(y = iconFloatY * 0.4f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * TV Mode Vertical Floating Matte-Black Pill Navigation Rail (#141416) with a glowing crimson-red
 * right rim (#D90429), spring-animated vertical liquid-wave bulge tracking the active/focused tab,
 * deep dark-crimson radial glow, and full Android TV D-Pad focus support.
 */
@Composable
fun FloatingLiquidWaveNavRail(
    currentTab: AppTab,
    onSelectTab: (AppTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = remember { AppTab.values().toList() }
    val selectedIndex = tabs.indexOf(currentTab).coerceAtLeast(0)
    var focusedIndex by remember { androidx.compose.runtime.mutableIntStateOf(-1) }
    val targetWaveIndex = if (focusedIndex >= 0) focusedIndex else selectedIndex

    val animatedIndex by animateFloatAsState(
        targetValue = targetWaveIndex.toFloat(),
        animationSpec = spring(
            dampingRatio = 0.72f,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "tv_liquid_wave_tab_index"
    )

    val pillShape = RoundedCornerShape(28.dp)

    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 10.dp, end = 6.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .width(92.dp)
                .fillMaxHeight()
                .shadow(
                    elevation = 18.dp,
                    shape = pillShape,
                    ambientColor = CrimsonGlow.copy(alpha = 0.45f),
                    spotColor = CrimsonGlow.copy(alpha = 0.55f)
                )
                .clip(pillShape)
        ) {
            // Custom Canvas: Matte-black vertical pill + crimson radial glow + vertical liquid wave + glowing right rim
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val cornerPx = 28.dp.toPx()
                val verticalPad = 14.dp.toPx()
                val usableHeight = (h - verticalPad * 2f).coerceAtLeast(1f)
                val tabHeight = usableHeight / tabs.size.toFloat()
                val activeCenterY = verticalPad + tabHeight * (animatedIndex + 0.5f)

                val pillPath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(0f, 0f, w, h),
                            cornerRadius = CornerRadius(cornerPx, cornerPx)
                        )
                    )
                }

                clipPath(pillPath) {
                    // 1. Matte-black pill base (#141416)
                    drawRect(color = FloatingNavSurface)

                    // 2. Subtle left glass sheen for matte depth
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.06f),
                                Color.Transparent
                            ),
                            startX = 0f,
                            endX = w * 0.45f
                        )
                    )

                    // 3. Deep dark-crimson radial glow radiating left from the active vertical liquid wave
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                CrimsonBright.copy(alpha = 0.46f),
                                CrimsonGlow.copy(alpha = 0.25f),
                                Color(0xFF4A000E).copy(alpha = 0.12f),
                                Color.Transparent
                            ),
                            center = Offset(w * 0.88f, activeCenterY),
                            radius = tabHeight * 1.1f
                        ),
                        center = Offset(w * 0.88f, activeCenterY),
                        radius = tabHeight * 1.1f
                    )

                    // 4. Smooth inward crimson-red liquid wave / hill bulge along the right edge
                    val waveHalfSpan = (tabHeight * 0.65f).coerceAtLeast(36.dp.toPx())
                    val baseRimX = w - 3.dp.toPx()
                    val peakX = w - 15.dp.toPx()
                    val topY = activeCenterY - waveHalfSpan
                    val bottomY = activeCenterY + waveHalfSpan

                    val waveFillPath = Path().apply {
                        moveTo(w, 0f)
                        lineTo(baseRimX, 0f)
                        lineTo(baseRimX, topY.coerceAtLeast(0f))
                        cubicTo(
                            baseRimX, activeCenterY - waveHalfSpan * 0.46f,
                            peakX, activeCenterY - waveHalfSpan * 0.34f,
                            peakX, activeCenterY
                        )
                        cubicTo(
                            peakX, activeCenterY + waveHalfSpan * 0.34f,
                            baseRimX, activeCenterY + waveHalfSpan * 0.46f,
                            baseRimX, bottomY.coerceAtMost(h)
                        )
                        lineTo(baseRimX, h)
                        lineTo(w, h)
                        close()
                    }

                    drawPath(
                        path = waveFillPath,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                CrimsonBright.copy(alpha = 0.95f),
                                CrimsonGlow.copy(alpha = 0.90f),
                                Color(0xFF7A0016)
                            ),
                            startX = peakX,
                            endX = w
                        )
                    )

                    // 5. Glowing crimson right rim stroke rising smoothly over the vertical liquid crest
                    val waveStrokePath = Path().apply {
                        moveTo(baseRimX, cornerPx * 0.35f)
                        lineTo(baseRimX, topY.coerceAtLeast(cornerPx * 0.35f))
                        cubicTo(
                            baseRimX, activeCenterY - waveHalfSpan * 0.46f,
                            peakX, activeCenterY - waveHalfSpan * 0.34f,
                            peakX, activeCenterY
                        )
                        cubicTo(
                            peakX, activeCenterY + waveHalfSpan * 0.34f,
                            baseRimX, activeCenterY + waveHalfSpan * 0.46f,
                            baseRimX, bottomY.coerceAtMost(h - cornerPx * 0.35f)
                        )
                        lineTo(baseRimX, h - cornerPx * 0.35f)
                    }

                    drawPath(
                        path = waveStrokePath,
                        color = CrimsonBright.copy(alpha = 0.45f),
                        style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawPath(
                        path = waveStrokePath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                CrimsonGlow.copy(alpha = 0.55f),
                                Color(0xFFFF4D6D),
                                CrimsonGlow.copy(alpha = 0.55f)
                            )
                        ),
                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                // 6. Subtle outer pill perimeter stroke (faint glass left, crimson right tint)
                drawRoundRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.12f),
                            CrimsonGlow.copy(alpha = 0.25f),
                            CrimsonGlow.copy(alpha = 0.85f)
                        )
                    ),
                    cornerRadius = CornerRadius(cornerPx, cornerPx),
                    style = Stroke(width = 1.2.dp.toPx())
                )
            }

            // 5 Interactive D-Pad Focusable Tab Items
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 14.dp, horizontal = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                tabs.forEachIndexed { index, tab ->
                    val isSelected = tab == currentTab
                    var isFocused by remember { androidx.compose.runtime.mutableStateOf(false) }
                    val isActiveVisual = isSelected || isFocused

                    val iconTint by animateColorAsState(
                        targetValue = if (isActiveVisual) Color(0xFFFF2E54) else Color(0xFF94A3B8),
                        animationSpec = tween(200),
                        label = "tv_tab_icon_tint_${tab.name}"
                    )
                    val labelTint by animateColorAsState(
                        targetValue = when {
                            isFocused -> Color.White
                            isSelected -> Color(0xFFFF4D6D)
                            else -> Color(0xFF94A3B8)
                        },
                        animationSpec = tween(200),
                        label = "tv_tab_label_tint_${tab.name}"
                    )
                    val itemOffsetX by animateDpAsState(
                        targetValue = if (isActiveVisual) (-3).dp else 0.dp,
                        animationSpec = spring(dampingRatio = 0.65f, stiffness = Spring.StiffnessMedium),
                        label = "tv_tab_offset_${tab.name}"
                    )
                    val interactionSource = remember { MutableInteractionSource() }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .then(
                                if (isFocused) {
                                    Modifier.border(
                                        width = 1.5.dp,
                                        color = CrimsonBright,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                } else {
                                    Modifier
                                }
                            )
                            .onFocusChanged { state ->
                                isFocused = state.isFocused
                                if (state.isFocused) {
                                    focusedIndex = index
                                } else if (focusedIndex == index) {
                                    focusedIndex = -1
                                }
                            }
                            .focusable()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { onSelectTab(tab) }
                            .padding(horizontal = 4.dp)
                            .testTag("tv_nav_tab_${tab.name.lowercase()}"),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = when (tab) {
                                AppTab.LIVE_TV -> Icons.Rounded.Tv
                                AppTab.EVENTS -> Icons.Rounded.EmojiEvents
                                AppTab.MOVIES -> Icons.Rounded.Movie
                                AppTab.PLAYLIST -> Icons.Rounded.Folder
                                AppTab.MENU -> Icons.Rounded.Menu
                            },
                            contentDescription = tab.englishLabel,
                            tint = iconTint,
                            modifier = Modifier
                                .size(if (isActiveVisual) 25.dp else 22.dp)
                                .offset(x = itemOffsetX)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tab.englishLabel,
                            color = labelTint,
                            fontFamily = InterFontFamily,
                            fontSize = 10.5.sp,
                            fontWeight = if (isActiveVisual) FontWeight.Bold else FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.offset(x = itemOffsetX * 0.4f)
                        )
                    }
                }
            }
        }
    }
}


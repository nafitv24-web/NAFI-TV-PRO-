package com.example.ui

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.util.ExternalPlayerHelper

@Composable
fun ExternalPlayerDialog(
    streamUrl: String,
    title: String,
    headers: Map<String, String> = emptyMap(),
    onDismiss: () -> Unit,
    onSelectInAppPlayer: () -> Unit
) {
    val context = LocalContext.current
    val knownPlayers = remember(context) { ExternalPlayerHelper.getKnownPlayers(context) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Rounded.SmartDisplay,
                contentDescription = null,
                tint = Color(0xFF00E5FF),
                modifier = Modifier.size(34.dp)
            )
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "প্লেয়ার ইঞ্জিন নির্বাচন করুন",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title.ifBlank { "Live Stream" },
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Option 0: Built-in ExoPlayer (Default)
                PlayerOptionCard(
                    title = "ইন-অ্যাপ এক্সোপ্লেয়ার (ExoPlayer)",
                    subTitle = "আল্ট্রা-ফাস্ট লোডিং, অটো কোয়ালিটি ও বাটার-স্মুথ ভিউ",
                    badgeText = "ডিফল্ট",
                    badgeColor = Color(0xFF00E5FF),
                    icon = Icons.Rounded.PlayCircle,
                    iconTint = Color(0xFF00E5FF),
                    onClick = {
                        onSelectInAppPlayer()
                        onDismiss()
                    }
                )

                // Dedicated options for Televizo, Network Stream Player, VLC, and MX Player
                knownPlayers.forEach { player ->
                    val isTelevizo = player.packageName == ExternalPlayerHelper.PACKAGE_TELEVIZO
                    val isNetworkStream = player.packageName == ExternalPlayerHelper.PACKAGE_NETWORK_STREAM_PLAYER
                    val isVlc = player.packageName == ExternalPlayerHelper.PACKAGE_VLC

                    val accentColor = when {
                        isTelevizo -> Color(0xFF38BDF8)
                        isNetworkStream -> Color(0xFF34D399)
                        isVlc -> Color(0xFFFF9800)
                        else -> Color(0xFFA855F7)
                    }

                    PlayerOptionCard(
                        title = player.displayName,
                        subTitle = player.description,
                        badgeText = if (player.isInstalled) "ইনস্টল আছে" else "ইনস্টল নেই",
                        badgeColor = if (player.isInstalled) Color(0xFF10B981) else Color(0xFFF59E0B),
                        icon = if (isVlc) Icons.Rounded.Traffic else if (isTelevizo) Icons.Rounded.Tv else Icons.Rounded.Language,
                        iconTint = accentColor,
                        onClick = {
                            if (player.isInstalled) {
                                ExternalPlayerHelper.launchStream(
                                    context = context,
                                    streamUrl = streamUrl,
                                    title = title,
                                    headers = headers,
                                    targetPackage = player.packageName,
                                    targetActivity = player.activityName
                                )
                                onDismiss()
                            } else {
                                ExternalPlayerHelper.openPlayStore(context, player.packageName)
                            }
                        }
                    )
                }

                // Option: System Chooser (Other Players)
                PlayerOptionCard(
                    title = "অন্যান্য প্লেয়ার (System Chooser)",
                    subTitle = "ফোনে ইনস্টল থাকা যেকোনো ভিডিও প্লেয়ার বেছে নিন",
                    badgeText = "সিস্টেম",
                    badgeColor = Color(0xFF94A3B8),
                    icon = Icons.Rounded.OpenInNew,
                    iconTint = Color.White,
                    onClick = {
                        ExternalPlayerHelper.launchStream(
                            context = context,
                            streamUrl = streamUrl,
                            title = title,
                            headers = headers,
                            targetPackage = null,
                            targetActivity = null
                        )
                        onDismiss()
                    }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF00E5FF))
            ) {
                Text("বন্ধ করুন", fontWeight = FontWeight.Bold)
            }
        },
        containerColor = Color(0xFF0F172A),
        shape = RoundedCornerShape(18.dp)
    )
}

@Composable
private fun PlayerOptionCard(
    title: String,
    subTitle: String,
    badgeText: String,
    badgeColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(1.dp, Color(0xFF334155))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF0F172A)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = badgeColor.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, badgeColor.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = badgeText,
                            color = badgeColor,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = subTitle,
                    color = Color(0xFF94A3B8),
                    fontSize = 10.5.sp,
                    lineHeight = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

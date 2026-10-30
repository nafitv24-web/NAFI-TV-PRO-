package com.example.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast

/**
 * External Player Integration Helper.
 * Integrates external streaming players:
 * 1. Televizo (com.ottplay.ottplay / com.ottplay.ottplay.StartActivity)
 * 2. Network Stream (Video) Player (com.genuine.leone / com.genuine.leone.ui.splash.SplashActivity)
 * 3. VLC for Android (org.videolan.vlc / org.videolan.vlc.StartActivity)
 * 4. System Chooser (Any installed media player)
 */
object ExternalPlayerHelper {

    const val PACKAGE_TELEVIZO = "com.ottplay.ottplay"
    const val ACTIVITY_TELEVIZO = "com.ottplay.ottplay.StartActivity"

    const val PACKAGE_NETWORK_STREAM_PLAYER = "com.genuine.leone"
    const val ACTIVITY_NETWORK_STREAM_PLAYER = "com.genuine.leone.ui.splash.SplashActivity"

    const val PACKAGE_VLC = "org.videolan.vlc"
    const val ACTIVITY_VLC = "org.videolan.vlc.StartActivity"

    const val PACKAGE_MX_PLAYER = "com.mxtech.videoplayer.ad"
    const val PACKAGE_MX_PLAYER_PRO = "com.mxtech.videoplayer.pro"

    data class PlayerAppInfo(
        val id: String,
        val displayName: String,
        val packageName: String,
        val activityName: String?,
        val description: String,
        val isInstalled: Boolean,
        val iconResId: Int? = null
    )

    /**
     * Checks whether an Android package is installed on the device.
     */
    fun isPackageInstalled(context: Context, packageName: String): Boolean {
        return try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(packageName, 0)
            }
            true
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Returns a list of known external player apps with their installation status.
     */
    fun getKnownPlayers(context: Context): List<PlayerAppInfo> {
        val televizoInstalled = isPackageInstalled(context, PACKAGE_TELEVIZO)
        val networkStreamInstalled = isPackageInstalled(context, PACKAGE_NETWORK_STREAM_PLAYER)
        val vlcInstalled = isPackageInstalled(context, PACKAGE_VLC)
        val mxInstalled = isPackageInstalled(context, PACKAGE_MX_PLAYER) || isPackageInstalled(context, PACKAGE_MX_PLAYER_PRO)

        return listOf(
            PlayerAppInfo(
                id = "televizo",
                displayName = "Televizo IPTV Player",
                packageName = PACKAGE_TELEVIZO,
                activityName = ACTIVITY_TELEVIZO,
                description = "লাইভ টিভি ও আইপিটিভি স্ট্রিমিং স্পেশালিস্ট",
                isInstalled = televizoInstalled
            ),
            PlayerAppInfo(
                id = "network_stream",
                displayName = "Network Stream (Video) Player",
                packageName = PACKAGE_NETWORK_STREAM_PLAYER,
                activityName = ACTIVITY_NETWORK_STREAM_PLAYER,
                description = "হাই স্পিড নেটওয়ার্ক প্রোটোকল ও বাফারিং সাপোর্ট",
                isInstalled = networkStreamInstalled
            ),
            PlayerAppInfo(
                id = "vlc",
                displayName = "VLC for Android",
                packageName = PACKAGE_VLC,
                activityName = ACTIVITY_VLC,
                description = "ইউনিভার্সাল অডিও/ভিডিও কোডেক ও LibVLC ইঞ্জিন",
                isInstalled = vlcInstalled
            ),
            PlayerAppInfo(
                id = "mx",
                displayName = "MX Player",
                packageName = if (isPackageInstalled(context, PACKAGE_MX_PLAYER_PRO)) PACKAGE_MX_PLAYER_PRO else PACKAGE_MX_PLAYER,
                activityName = null,
                description = "হার্ডওয়্যার অ্যাকসিলারেশন ও জেস্টার কন্ট্রোল",
                isInstalled = mxInstalled
            )
        )
    }

    /**
     * Detects appropriate MIME type based on URL structure.
     */
    fun inferMimeType(url: String): String {
        val clean = url.substringBefore('?').lowercase()
        return when {
            clean.endsWith(".m3u8") || url.contains(".m3u8", ignoreCase = true) || url.contains("/hls/", ignoreCase = true) -> "application/x-mpegURL"
            clean.endsWith(".mpd") || url.contains(".mpd", ignoreCase = true) || url.contains("/dash/", ignoreCase = true) -> "application/dash+xml"
            clean.endsWith(".ts") || url.contains(".ts?", ignoreCase = true) || url.contains("output=ts", ignoreCase = true) -> "video/mp2t"
            clean.endsWith(".mp4") -> "video/mp4"
            clean.endsWith(".mkv") -> "video/x-matroska"
            clean.endsWith(".webm") -> "video/webm"
            clean.endsWith(".flv") -> "video/x-flv"
            clean.endsWith(".avi") -> "video/avi"
            else -> "video/*"
        }
    }

    /**
     * Launches the stream in a specific external player or system chooser.
     * Passes all necessary headers (User-Agent, Referer, Origin, Cookie) in
     * the standard intent extras accepted by Televizo, Network Stream Player, and VLC.
     */
    fun launchStream(
        context: Context,
        streamUrl: String,
        title: String,
        headers: Map<String, String> = emptyMap(),
        targetPackage: String? = null,
        targetActivity: String? = null
    ): Boolean {
        try {
            if (streamUrl.isBlank()) {
                Toast.makeText(context, "স্ট্রিমিং লিংক পাওয়া যায়নি", Toast.LENGTH_SHORT).show()
                return false
            }

            val uri = Uri.parse(streamUrl)
            val mimeType = inferMimeType(streamUrl)

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, mimeType)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                // Title extras
                putExtra("title", title)
                putExtra("android.intent.extra.TITLE", title)
                putExtra("media_title", title)

                // Headers bundle (Standard for Android MediaPlayer & VLC & Network Stream Player)
                if (headers.isNotEmpty()) {
                    val headersBundle = Bundle()
                    val headerStrings = ArrayList<String>()

                    headers.forEach { (key, value) ->
                        if (value.isNotBlank()) {
                            headersBundle.putString(key, value)
                            headerStrings.add("$key: $value")
                        }
                    }

                    putExtra("android.media.intent.extra.HTTP_HEADERS", headersBundle)
                    putExtra("headers", headerStrings.toTypedArray())
                    putExtra("extra_headers", headersBundle)

                    headers["User-Agent"]?.let {
                        putExtra("user_agent", it)
                        putExtra("http-user-agent", it)
                    }
                    headers["Referer"]?.let {
                        putExtra("referer", it)
                        putExtra("http-referrer", it)
                    }
                }

                // Explicit component / package if provided
                if (!targetPackage.isNullOrBlank()) {
                    if (!targetActivity.isNullOrBlank()) {
                        component = ComponentName(targetPackage, targetActivity)
                    } else {
                        setPackage(targetPackage)
                    }
                }
            }

            // Verify if component can resolve
            val resolveInfo = context.packageManager.resolveActivity(intent, 0)
            if (resolveInfo != null) {
                context.startActivity(intent)
                val targetName = when (targetPackage) {
                    PACKAGE_TELEVIZO -> "Televizo"
                    PACKAGE_NETWORK_STREAM_PLAYER -> "Network Stream Player"
                    PACKAGE_VLC -> "VLC"
                    PACKAGE_MX_PLAYER, PACKAGE_MX_PLAYER_PRO -> "MX Player"
                    else -> "এক্সটার্নাল প্লেয়ার"
                }
                Toast.makeText(context, "$targetName-এ চালু হচ্ছে...", Toast.LENGTH_SHORT).show()
                return true
            }

            // If explicit target failed or wasn't installed, fall back to chooser
            val chooserIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, mimeType)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("title", title)
                putExtra("android.intent.extra.TITLE", title)
                if (headers.isNotEmpty()) {
                    val headersBundle = Bundle()
                    headers.forEach { (k, v) -> headersBundle.putString(k, v) }
                    putExtra("android.media.intent.extra.HTTP_HEADERS", headersBundle)
                }
            }
            val chooser = Intent.createChooser(chooserIntent, "ভিডিও প্লেয়ার বেছে নিন").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "এক্সটার্নাল প্লেয়ার খোলা যায়নি: ${e.localizedMessage ?: "ত্রুটি"}", Toast.LENGTH_LONG).show()
            return false
        }
    }

    /**
     * Opens Google Play Store to install a requested external player.
     */
    fun openPlayStore(context: Context, packageName: String) {
        try {
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(marketIntent)
        } catch (_: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(webIntent)
        }
    }
}

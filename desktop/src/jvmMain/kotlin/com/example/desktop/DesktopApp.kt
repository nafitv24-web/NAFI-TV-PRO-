package com.example.desktop

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.desktop.player.DesktopVlcPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.awt.Desktop
import java.net.URI
import java.util.concurrent.TimeUnit

data class DesktopMediaItem(
    val id: String,
    val title: String,
    val category: String,
    val streamUrl: String,
    val logoUrl: String? = null,
    val isLive: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesktopNafiTvApp() {
    var selectedTab by remember { mutableStateOf(0) } // 0: Live Events, 1: Live TV, 2: Sports Channels, 3: Movies
    var selectedCategoryFilter by remember { mutableStateOf("সবগুলো") }
    var searchQuery by remember { mutableStateOf("") }
    var currentPlayingItem by remember { mutableStateOf<DesktopMediaItem?>(null) }
    var mediaList by remember { mutableStateOf<List<DesktopMediaItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // High performance OkHttpClient with timeouts
    val client = remember {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

    fun extractUrls(raw: String): List<String> {
        if (raw.isBlank()) return emptyList()
        return raw.split("\n", "\r", ",")
            .map { it.trim() }
            .filter { it.startsWith("http://", ignoreCase = true) || it.startsWith("https://", ignoreCase = true) }
    }

    fun parseJsonMedia(content: String, defaultCat: String, isLive: Boolean): List<DesktopMediaItem> {
        val list = mutableListOf<DesktopMediaItem>()
        val trimmed = content.trim()
        if (trimmed.isEmpty()) return list

        try {
            if (trimmed.startsWith("{")) {
                val root = JSONObject(trimmed)

                // 1. Matches / Events schema (Sports_data.json, tapmad_data.json, fancode.json)
                val matchesArr = root.optJSONArray("matches")
                    ?: root.optJSONArray("Matches")
                    ?: root.optJSONArray("events")
                    ?: root.optJSONArray("data")

                if (matchesArr != null && matchesArr.length() > 0) {
                    for (i in 0 until matchesArr.length()) {
                        val mObj = matchesArr.optJSONObject(i) ?: continue
                        val vName = mObj.optString("event_name", "").ifBlank {
                            mObj.optString("VideoName", mObj.optString("title", mObj.optString("name", "")))
                        }.trim()
                        if (vName.isBlank()) continue

                        val catName = mObj.optString("CategoryName", "").ifBlank {
                            mObj.optString("Category", mObj.optString("category", defaultCat))
                        }.trim().ifBlank { defaultCat }

                        val thumb = mObj.optString("ThumbnailStandard", "").ifBlank {
                            mObj.optString("image", mObj.optString("thumbnail", mObj.optString("logo", "")))
                        }.trim().takeIf { it.isNotBlank() }

                        val status = mObj.optString("Status", mObj.optString("status", "Live")).trim()
                        val isLiveEvent = status.contains("live", ignoreCase = true) || isLive

                        // Check streams inside "streams" array
                        val streamsArr = mObj.optJSONArray("streams")
                        var addedStream = false
                        if (streamsArr != null && streamsArr.length() > 0) {
                            for (sIdx in 0 until streamsArr.length()) {
                                val sObj = streamsArr.optJSONObject(sIdx) ?: continue
                                val sUrl = sObj.optString("stream_url", sObj.optString("url", "")).trim()
                                if (sUrl.startsWith("http://", ignoreCase = true) || sUrl.startsWith("https://", ignoreCase = true)) {
                                    val chName = sObj.optString("channel_name", "").trim()
                                    val itemTitle = if (chName.isNotBlank() && streamsArr.length() > 1) "$vName ($chName)" else vName
                                    list.add(
                                        DesktopMediaItem(
                                            id = "event_${list.size}_${sIdx}",
                                            title = itemTitle,
                                            category = catName,
                                            streamUrl = sUrl,
                                            logoUrl = thumb,
                                            isLive = isLiveEvent
                                        )
                                    )
                                    addedStream = true
                                }
                            }
                        }

                        // Top-level stream_url or url fallback
                        if (!addedStream) {
                            val topStream = mObj.optString("stream_url", mObj.optString("streamUrl", mObj.optString("url", mObj.optString("link", "")))).trim()
                            if (topStream.startsWith("http://", ignoreCase = true) || topStream.startsWith("https://", ignoreCase = true)) {
                                list.add(
                                    DesktopMediaItem(
                                        id = "event_${list.size}",
                                        title = vName,
                                        category = catName,
                                        streamUrl = topStream,
                                        logoUrl = thumb,
                                        isLive = isLiveEvent
                                    )
                                )
                            }
                        }
                    }
                }

                // 2. Categories / Movies / Channels schema (e.g. Update Channel.m3u [JSON], movies.json)
                val catArr = root.optJSONArray("categories")
                    ?: root.optJSONArray("list")
                    ?: root.optJSONArray("data")
                    ?: root.optJSONArray("movies")

                if (catArr != null && catArr.length() > 0) {
                    for (cIdx in 0 until catArr.length()) {
                        val cObj = catArr.optJSONObject(cIdx) ?: continue
                        val catTitle = cObj.optString("category_name", cObj.optString("name", cObj.optString("title", defaultCat))).trim().ifBlank { defaultCat }

                        val innerItems = cObj.optJSONArray("movies")
                            ?: cObj.optJSONArray("channels")
                            ?: cObj.optJSONArray("items")
                            ?: cObj.optJSONArray("content")
                            ?: cObj.optJSONArray("streams")

                        if (innerItems != null) {
                            for (mIdx in 0 until innerItems.length()) {
                                val itemObj = innerItems.optJSONObject(mIdx) ?: continue
                                val title = itemObj.optString("title", itemObj.optString("name", itemObj.optString("channel_name", ""))).trim()
                                if (title.isBlank()) continue

                                val poster = itemObj.optString("poster", itemObj.optString("image", itemObj.optString("logo", itemObj.optString("thumbnail", "")))).trim().takeIf { it.isNotBlank() }

                                // Check sources array
                                var streamUrl = ""
                                val sources = itemObj.optJSONArray("sources")
                                if (sources != null && sources.length() > 0) {
                                    val firstSrc = sources.optJSONObject(0)
                                    streamUrl = firstSrc?.optString("url", firstSrc.optString("stream_url", ""))?.trim() ?: ""
                                }
                                if (streamUrl.isBlank()) {
                                    streamUrl = itemObj.optString("url", itemObj.optString("stream_url", itemObj.optString("link", ""))).trim()
                                }

                                if (streamUrl.startsWith("http://", ignoreCase = true) || streamUrl.startsWith("https://", ignoreCase = true)) {
                                    list.add(
                                        DesktopMediaItem(
                                            id = "json_${list.size}_${mIdx}",
                                            title = title,
                                            category = catTitle,
                                            streamUrl = streamUrl,
                                            logoUrl = poster,
                                            isLive = isLive
                                        )
                                    )
                                }
                            }
                        } else {
                            // Flat item inside category array
                            val title = cObj.optString("title", cObj.optString("name", "")).trim()
                            val stream = cObj.optString("url", cObj.optString("stream_url", "")).trim()
                            val poster = cObj.optString("poster", cObj.optString("image", cObj.optString("logo", ""))).trim().takeIf { it.isNotBlank() }
                            if (title.isNotBlank() && (stream.startsWith("http://", ignoreCase = true) || stream.startsWith("https://", ignoreCase = true))) {
                                list.add(
                                    DesktopMediaItem(
                                        id = "json_flat_${list.size}",
                                        title = title,
                                        category = defaultCat,
                                        streamUrl = stream,
                                        logoUrl = poster,
                                        isLive = isLive
                                    )
                                )
                            }
                        }
                    }
                }
            } else if (trimmed.startsWith("[")) {
                // Flat Array of items/channels/movies
                val rootArr = JSONArray(trimmed)
                for (i in 0 until rootArr.length()) {
                    val itemObj = rootArr.optJSONObject(i) ?: continue
                    val title = itemObj.optString("title", itemObj.optString("name", itemObj.optString("event_name", itemObj.optString("channel_name", "")))).trim()
                    if (title.isBlank()) continue

                    val cat = itemObj.optString("category_name", itemObj.optString("category", itemObj.optString("Category", defaultCat))).trim().ifBlank { defaultCat }
                    val poster = itemObj.optString("poster", itemObj.optString("image", itemObj.optString("logo", itemObj.optString("thumbnail", "")))).trim().takeIf { it.isNotBlank() }

                    var stream = itemObj.optString("stream_url", itemObj.optString("url", itemObj.optString("link", ""))).trim()
                    if (stream.isBlank()) {
                        val sources = itemObj.optJSONArray("sources") ?: itemObj.optJSONArray("streams")
                        if (sources != null && sources.length() > 0) {
                            val firstSrc = sources.optJSONObject(0)
                            stream = firstSrc?.optString("url", firstSrc.optString("stream_url", ""))?.trim() ?: ""
                        }
                    }

                    if (stream.startsWith("http://", ignoreCase = true) || stream.startsWith("https://", ignoreCase = true)) {
                        list.add(
                            DesktopMediaItem(
                                id = "arr_${list.size}_$i",
                                title = title,
                                category = cat,
                                streamUrl = stream,
                                logoUrl = poster,
                                isLive = isLive
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun parseM3uMedia(content: String, defaultCat: String, isLive: Boolean): List<DesktopMediaItem> {
        val list = mutableListOf<DesktopMediaItem>()
        val lines = content.lines()
        var curTitle = ""
        var curLogo: String? = null
        var curGroup = defaultCat

        for (line in lines) {
            val trimmed = line.trim()
            if (trimmed.startsWith("#EXTINF:", ignoreCase = true)) {
                curTitle = trimmed.substringAfterLast(",").trim()
                if (trimmed.contains("tvg-logo=\"")) {
                    curLogo = trimmed.substringAfter("tvg-logo=\"").substringBefore("\"").trim().takeIf { it.isNotBlank() }
                }
                if (trimmed.contains("group-title=\"")) {
                    curGroup = trimmed.substringAfter("group-title=\"").substringBefore("\"").trim().ifBlank { defaultCat }
                }
            } else if (trimmed.startsWith("http://", ignoreCase = true) || trimmed.startsWith("https://", ignoreCase = true)) {
                if (curTitle.isNotBlank()) {
                    var finalUrl = trimmed
                    if (finalUrl.contains(" ")) {
                        finalUrl = finalUrl.replace(" ", "%20")
                    }
                    list.add(
                        DesktopMediaItem(
                            id = "m3u_${list.size}",
                            title = curTitle,
                            category = curGroup,
                            streamUrl = finalUrl,
                            logoUrl = curLogo,
                            isLive = isLive
                        )
                    )
                    curTitle = ""
                    curLogo = null
                    curGroup = defaultCat
                }
            }
        }
        return list
    }

    fun fetchAndParseMediaUrl(rawUrl: String, defaultCat: String, isLive: Boolean): List<DesktopMediaItem> {
        try {
            var sanitized = rawUrl.trim()
            if (sanitized.contains(" ")) {
                sanitized = sanitized.replace(" ", "%20")
            }
            if (sanitized.contains("|")) {
                sanitized = sanitized.substringBefore("|").trim()
            }
            if (!sanitized.startsWith("http://", ignoreCase = true) && !sanitized.startsWith("https://", ignoreCase = true)) {
                return emptyList()
            }

            val req = Request.Builder()
                .url(sanitized)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .build()

            val resp = client.newCall(req).execute()
            if (!resp.isSuccessful) return emptyList()

            val body = resp.body?.string()?.trim() ?: return emptyList()
            if (body.isEmpty()) return emptyList()

            return if (body.startsWith("{") || body.startsWith("[")) {
                parseJsonMedia(body, defaultCat, isLive)
            } else {
                parseM3uMedia(body, defaultCat, isLive)
            }
        } catch (e: Exception) {
            println("Error fetching URL ($rawUrl): ${e.message}")
            return emptyList()
        }
    }

    fun getFallbackItems(tab: Int): List<DesktopMediaItem> {
        return when (tab) {
            0 -> listOf(
                DesktopMediaItem("fb_ev_1", "Bangladesh vs Sri Lanka (Live Sports)", "ক্রিকেট", "https://tvsen5.aynaott.com/TnMn5kZz8aLm/index.m3u8", isLive = true),
                DesktopMediaItem("fb_ev_2", "India vs Australia T20 (Sony Ten)", "ক্রিকেট", "https://flussonic.defineatoz.com/Starsports1/tracks-v1a1/mono.m3u8", isLive = true),
                DesktopMediaItem("fb_ev_3", "Real Madrid vs Barcelona (El Clasico)", "ফুটবল", "https://tvsen6.aynaott.com/zv68oqPDu7MZZwmHhRxt/tracks-v1a1/mono.ts.m3u8", isLive = true),
                DesktopMediaItem("fb_ev_4", "Manchester City vs Arsenal", "ফুটবল", "https://tvsen5.aynaott.com/willowhd/index.m3u8", isLive = true)
            )
            1 -> listOf(
                DesktopMediaItem("fb_tv_1", "BTV National", "বাংলাদেশ", "https://i.ibb.co.com/k2R3QmKF/Picsart-24-10-21-19-38-26-976.webp", isLive = true),
                DesktopMediaItem("fb_tv_2", "Somoy TV Live HD", "সংবাদ", "https://box.bbaria.net:8083/Nagorik_TV/video.m3u8", isLive = true),
                DesktopMediaItem("fb_tv_3", "Jamuna TV HD", "সংবাদ", "http://livetv.akr4m.com:8080/bdtv/restrem/38.m3u8", isLive = true),
                DesktopMediaItem("fb_tv_4", "T Sports Live HD", "খেলাধুলা", "https://tvsen5.aynaott.com/TnMn5kZz8aLm/index.m3u8", isLive = true),
                DesktopMediaItem("fb_tv_5", "Channel 24 HD", "বিনোদন", "https://tvsen7.aynaott.com/ssport2hd/index.m3u8", isLive = true)
            )
            2 -> listOf(
                DesktopMediaItem("fb_sp_1", "T Sports HD", "লাইভ স্পোর্টস", "https://tvsen5.aynaott.com/TnMn5kZz8aLm/index.m3u8", isLive = true),
                DesktopMediaItem("fb_sp_2", "Sony Sports Ten 1 HD", "স্পোর্টস টিভি", "https://flussonic.defineatoz.com/PTV/index.m3u8", isLive = true),
                DesktopMediaItem("fb_sp_3", "Star Sports 1 HD", "স্পোর্টস টিভি", "https://flussonic.defineatoz.com/Starsports1/tracks-v1a1/mono.m3u8", isLive = true),
                DesktopMediaItem("fb_sp_4", "Willow Sports Live", "ক্রিকেট", "https://tvsen5.aynaott.com/willowhd/index.m3u8", isLive = true),
                DesktopMediaItem("fb_sp_5", "A Sports HD", "ক্রিকেট", "https://tvsen6.aynaott.com/zv68oqPDu7MZZwmHhRxt/tracks-v1a1/mono.ts.m3u8", isLive = true)
            )
            else -> listOf(
                DesktopMediaItem("fb_mv_1", "Tufan (তুফান - Full Movie HD)", "বাংলা মুভি", "https://drk6xq0vhn.gpcdn.net/live/euro_sports_hd_abr/index.m3u8", isLive = false),
                DesktopMediaItem("fb_mv_2", "Jawan (জাওয়ান - HD Bangla Dubbed)", "হিন্দি মুভি", "https://flussonic.defineatoz.com/starsportsselect1/tracks-v1/mono.m3u8", isLive = false),
                DesktopMediaItem("fb_mv_3", "Animal (HD Remastered)", "অ্যাকশন মুভি", "https://tvsen7.aynaott.com/ssport2hd/index.m3u8", isLive = false)
            )
        }
    }

    fun loadContent(tab: Int) {
        isLoading = true
        selectedCategoryFilter = "সবগুলো"
        coroutineScope.launch(Dispatchers.IO) {
            val collectedItems = mutableListOf<DesktopMediaItem>()

            // Default URLs matching Firebase configuration
            var liveTvUrls = listOf(
                "https://raw.githubusercontent.com/nafitv24-web/NAFI-TV/refs/heads/main/Update%20Channel.m3u",
                "https://raw.githubusercontent.com/nafitv24-web/NAFI-TV/refs/heads/main/HOST%20Channel%20NF.m3u",
                "https://raw.githubusercontent.com/sm-monirulislam/Toffee-Auto-Update/refs/heads/main/toffee_playlist.m3u",
                "https://raw.githubusercontent.com/srhady/Hady/refs/heads/main/akash_live.m3u"
            )
            var sportsUrls = listOf(
                "https://raw.githubusercontent.com/sm-monirulislam/SonyLiv_Event_Playlist/refs/heads/main/sonyLiv.m3u",
                "https://raw.githubusercontent.com/nafitv24-web/NAFI-TV/refs/heads/main/NAFI%20SPORTS.m3u",
                "https://raw.githubusercontent.com/sm-monirulislam/Tapmad_Auto_Update_Playlist/refs/heads/main/tapmad_sm.m3u",
                "https://gist.githubusercontent.com/albatr0ssss/3cff7a26be49b1d352c15f615067e7cd/raw/tapmad_bd.m3u",
                "https://raw.githubusercontent.com/srhady/SonyLiv/refs/heads/main/sonyliv_playlist.m3u",
                "https://raw.githubusercontent.com/srhady/willow-event/refs/heads/main/live_sports.m3u",
                "https://raw.githubusercontent.com/srhady/willow-event/refs/heads/main/primevideo_sports.m3u",
                "https://raw.githubusercontent.com/sm-monirulislam/Upcoming-and-Live-Sports-Data/refs/heads/main/Sports_data.m3u",
                "https://raw.githubusercontent.com/srhady/axsports/refs/heads/main/playlist.m3u"
            )
            var moviesUrls = listOf(
                "https://raw.githubusercontent.com/nafitv24-web/NAFI-TV/refs/heads/main/Kolkata%20serial.json",
                "https://raw.githubusercontent.com/nafitv24-web/NAFI-TV/refs/heads/main/movies.json",
                "https://raw.githubusercontent.com/abusaeeidx/Movie-Playlist-Auto-update/refs/heads/main/Mix_Movies.m3u",
                "https://raw.githubusercontent.com/srhady/join_telegram_chennal-livesportsplay/refs/heads/main/latest_movies.m3u",
                "https://raw.githubusercontent.com/abusaeeidx/Movie-Playlist-Auto-update/refs/heads/main/Bangla_Movies.m3u"
            )
            var liveEventsUrls = listOf(
                "https://raw.githubusercontent.com/sm-monirulislam/Upcoming-and-Live-Sports-Data/main/Sports_data.json",
                "https://raw.githubusercontent.com/sm-monirulislam/Tapmad_Auto_Update_Playlist/main/tapmad_data.json",
                "https://raw.githubusercontent.com/kajju027/Fancode-Events-Json/main/fancode.json"
            )

            // 1. Fetch Remote Firebase Realtime Database Configuration
            try {
                val rtdbUrls = listOf(
                    "https://nafitv24-live-default-rtdb.firebaseio.com/app_config.json",
                    "https://nafitv24-default-rtdb.asia-southeast1.firebasedatabase.app/app_config.json"
                )

                for (fbUrl in rtdbUrls) {
                    try {
                        val configReq = Request.Builder().url(fbUrl).build()
                        val configResp = client.newCall(configReq).execute()
                        if (configResp.isSuccessful) {
                            val body = configResp.body?.string()
                            if (!body.isNullOrBlank() && body.startsWith("{")) {
                                val obj = JSONObject(body)
                                if (obj.has("liveTvM3uUrl")) {
                                    val extracted = extractUrls(obj.getString("liveTvM3uUrl"))
                                    if (extracted.isNotEmpty()) liveTvUrls = extracted
                                }
                                if (obj.has("sportsM3uUrl")) {
                                    val extracted = extractUrls(obj.getString("sportsM3uUrl"))
                                    if (extracted.isNotEmpty()) sportsUrls = extracted
                                }
                                if (obj.has("moviesM3uUrl")) {
                                    val extracted = extractUrls(obj.getString("moviesM3uUrl"))
                                    if (extracted.isNotEmpty()) moviesUrls = extracted
                                }
                                if (obj.has("tapmadJsonUrl")) {
                                    val extracted = extractUrls(obj.getString("tapmadJsonUrl"))
                                    if (extracted.isNotEmpty()) liveEventsUrls = extracted
                                }
                                break
                            }
                        }
                    } catch (_: Exception) {}
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // 2. Tab Specific Remote Data Fetching
            when (tab) {
                0 -> {
                    // LIVE EVENTS: Fetch all event URLs in parallel
                    val deferredEvents = liveEventsUrls.map { evUrl ->
                        async { fetchAndParseMediaUrl(evUrl, defaultCat = "লাইভ ম্যাচ", isLive = true) }
                    }
                    val fetched = deferredEvents.awaitAll().flatten()
                    collectedItems.addAll(fetched)

                    // Also fetch Firebase RTDB /matches.json directly for real-time matches
                    try {
                        val matchesReq = Request.Builder()
                            .url("https://nafitv24-live-default-rtdb.firebaseio.com/matches.json")
                            .build()
                        val matchesResp = client.newCall(matchesReq).execute()
                        if (matchesResp.isSuccessful) {
                            val mBody = matchesResp.body?.string()?.trim() ?: ""
                            if (mBody.startsWith("{")) {
                                val mObj = JSONObject(mBody)
                                val keys = mObj.keys()
                                while (keys.hasNext()) {
                                    val key = keys.next()
                                    val item = mObj.optJSONObject(key) ?: continue
                                    val name = item.optString("name", item.optString("title", "")).trim()
                                    val stream = item.optString("streamUrl", item.optString("backupUrl", "")).trim()
                                    val cat = item.optString("category", "ক্রিকেট").trim().ifBlank { "ক্রিকেট" }
                                    val logo = item.optString("logoUrl", item.optString("logo", item.optString("poster", ""))).trim().takeIf { it.isNotBlank() }
                                    val isLiveNow = item.optBoolean("isLive", true)

                                    if (name.isNotBlank() && (stream.startsWith("http://") || stream.startsWith("https://"))) {
                                        collectedItems.add(
                                            0,
                                            DesktopMediaItem(
                                                id = "rtdb_match_$key",
                                                title = name,
                                                category = cat,
                                                streamUrl = stream,
                                                logoUrl = logo,
                                                isLive = isLiveNow
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    } catch (_: Exception) {}
                }
                1 -> {
                    // LIVE TV: Fetch all Live TV URLs in parallel
                    val deferredTv = liveTvUrls.map { tvUrl ->
                        async { fetchAndParseMediaUrl(tvUrl, defaultCat = "লাইভ টিভি", isLive = true) }
                    }
                    collectedItems.addAll(deferredTv.awaitAll().flatten())
                }
                2 -> {
                    // SPORTS TV: Fetch all Sports playlist URLs in parallel
                    val deferredSports = sportsUrls.map { spUrl ->
                        async { fetchAndParseMediaUrl(spUrl, defaultCat = "স্পোর্টস টিভি", isLive = true) }
                    }
                    collectedItems.addAll(deferredSports.awaitAll().flatten())
                }
                3 -> {
                    // MOVIES & SERIES: Fetch all Movie catalogs in parallel
                    val deferredMovies = moviesUrls.map { mvUrl ->
                        async { fetchAndParseMediaUrl(mvUrl, defaultCat = "মুভিজ", isLive = false) }
                    }
                    collectedItems.addAll(deferredMovies.awaitAll().flatten())
                }
            }

            // 3. Fallback Safety Guarantee: If remote returned 0 items, use built-in channels
            if (collectedItems.isEmpty()) {
                collectedItems.addAll(getFallbackItems(tab))
            }

            // 4. De-duplicate by title & streamUrl
            val seen = HashSet<String>()
            val distinctList = collectedItems.filter { item ->
                val signature = "${item.title.trim().lowercase()}_${item.streamUrl.trim()}"
                if (seen.contains(signature)) false else { seen.add(signature); true }
            }

            withContext(Dispatchers.Main) {
                mediaList = distinctList
                isLoading = false
            }
        }
    }

    LaunchedEffect(selectedTab) {
        loadContent(selectedTab)
    }

    // Extract unique categories for category filtering tabs
    val categoryList = remember(mediaList) {
        val cats = mediaList.map { it.category }.distinct().filter { it.isNotBlank() }
        listOf("সবগুলো") + cats
    }

    // Filtered by Search & Category
    val filteredList = remember(mediaList, searchQuery, selectedCategoryFilter) {
        mediaList.filter { item ->
            val matchQuery = searchQuery.isBlank() ||
                    item.title.contains(searchQuery, ignoreCase = true) ||
                    item.category.contains(searchQuery, ignoreCase = true)

            val matchCategory = selectedCategoryFilter == "সবগুলো" ||
                    item.category.equals(selectedCategoryFilter, ignoreCase = true)

            matchQuery && matchCategory
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0F172A)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // 1. Sidebar Navigation (PC Widescreen)
            NavigationRail(
                modifier = Modifier.width(220.dp).fillMaxHeight(),
                containerColor = Color(0xFF1E293B),
                header = {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF00E5FF),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Rounded.LiveTv, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("NAFI TV 24", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("PC Edition v3.1.5", color = Color(0xFF94A3B8), fontSize = 11.sp)
                    }
                }
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                NavigationRailItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Rounded.SportsCricket, contentDescription = null) },
                    label = { Text("লাইভ ইভেন্ট", fontWeight = FontWeight.SemiBold) }
                )
                NavigationRailItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Rounded.Tv, contentDescription = null) },
                    label = { Text("লাইভ টিভি", fontWeight = FontWeight.SemiBold) }
                )
                NavigationRailItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Rounded.SportsScore, contentDescription = null) },
                    label = { Text("স্পোর্টস টিভি", fontWeight = FontWeight.SemiBold) }
                )
                NavigationRailItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Rounded.Movie, contentDescription = null) },
                    label = { Text("মুভিজ", fontWeight = FontWeight.SemiBold) }
                )

                Spacer(modifier = Modifier.weight(1f))

                // Refresh Button
                OutlinedButton(
                    onClick = { loadContent(selectedTab) },
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Rounded.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("রিফ্রেশ", fontSize = 12.sp)
                }
            }

            // 2. Main Content Area
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight().padding(20.dp)
            ) {
                // Top Search Bar & Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("ম্যাচ বা চ্যানেলের নাম খুঁজুন...", color = Color(0xFF64748B)) },
                        leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null, tint = Color(0xFF38BDF8)) },
                        modifier = Modifier.width(420.dp),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Text(
                        text = "মোট চ্যানেল / ইভেন্ট: ${filteredList.size}",
                        color = Color(0xFF94A3B8),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Category Filter Chips
                if (categoryList.size > 2) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categoryList) { cat ->
                            FilterChip(
                                selected = selectedCategoryFilter == cat,
                                onClick = { selectedCategoryFilter = cat },
                                label = { Text(cat, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF00E5FF),
                                    selectedLabelColor = Color.Black,
                                    containerColor = Color(0xFF1E293B),
                                    labelColor = Color(0xFFCBD5E1)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }

                // Active Video Player Area (When an item is clicked)
                if (currentPlayingItem != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(400.dp).padding(bottom = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            // Video Player with full controller controls
                            DesktopVlcPlayer(
                                title = currentPlayingItem!!.title,
                                streamUrl = currentPlayingItem!!.streamUrl,
                                isLive = currentPlayingItem!!.isLive,
                                category = currentPlayingItem!!.category,
                                onClose = { currentPlayingItem = null },
                                modifier = Modifier.fillMaxSize()
                            )

                            // Title Overlay Top Bar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xCC0F172A))
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Rounded.PlayCircle, contentDescription = null, tint = Color(0xFF00E5FF), modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = currentPlayingItem!!.title,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = {
                                            try {
                                                if (Desktop.isDesktopSupported()) {
                                                    Desktop.getDesktop().browse(URI(currentPlayingItem!!.streamUrl))
                                                }
                                            } catch (_: Exception) {}
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.AutoMirrored.Rounded.OpenInNew, contentDescription = "Open Stream", tint = Color(0xFF00E5FF))
                                    }

                                    Spacer(modifier = Modifier.width(4.dp))

                                    IconButton(
                                        onClick = { currentPlayingItem = null },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Rounded.Close, contentDescription = "Close Player", tint = Color.White)
                                    }
                                }
                            }

                            // Persistent watermark at top-right of Desktop player (No background, bright & vivid)
                            Image(
                                painter = androidx.compose.ui.res.painterResource("nafi_watermark_logo.png"),
                                contentDescription = "NAFI TV PRO Logo",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp, top = 46.dp)
                                    .height(36.dp),
                                alpha = 0.90f,
                                contentScale = androidx.compose.ui.layout.ContentScale.Fit
                            )
                        }
                    }
                }

                // Grid Content
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = Color(0xFF00E5FF))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("লাইভ চ্যানেল ও কনটেন্ট লোড হচ্ছে...", color = Color(0xFF94A3B8), fontSize = 13.sp)
                        }
                    }
                } else if (filteredList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Rounded.SearchOff, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("কোনো কনটেন্ট পাওয়া যায়নি", color = Color(0xFF94A3B8), fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { loadContent(selectedTab) }) {
                                Text("পুনরায় লোড করুন")
                            }
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 190.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredList, key = { it.id }) { item ->
                            Card(
                                onClick = { currentPlayingItem = item },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                modifier = Modifier.fillMaxWidth().height(165.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(12.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = if (item.isLive) Color(0xFFEF4444) else Color(0xFF334155)
                                        ) {
                                            Text(
                                                text = if (item.isLive) "LIVE" else item.category,
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }

                                        Surface(
                                            shape = CircleShape,
                                            color = Color(0xFF0F172A),
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(Icons.Rounded.PlayArrow, contentDescription = null, tint = Color(0xFF00E5FF), modifier = Modifier.size(18.dp))
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = item.title,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.5.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
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

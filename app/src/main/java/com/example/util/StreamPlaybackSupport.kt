package com.example.util

import android.net.Uri
import android.util.Base64
import androidx.media3.common.C
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.TransferListener
import com.example.model.MediaType
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

enum class StreamKind {
    HLS,
    DASH,
    MPEG_TS,
    RTSP,
    PROGRESSIVE,
    EXTERNAL_PROTOCOL,
    EMBED
}

data class StreamClassification(
    val kind: StreamKind,
    val exoMimeType: String?,
    val isLiveStream: Boolean,
    val isTsStream: Boolean,
    val isXtreamStream: Boolean,
    val isStalkerStream: Boolean
)

/**
 * Universal stream URL normalizer, protocol/container classifier, and MPEG-TS sync aligner.
 * Ensures live Xtream/IPTV `.ts` streams (including mid-packet TCP joins and HLS redirects),
 * `.m3u8`, `.mpd`, `.mp4`, `.mkv`, `.flv`, `.avi`, `.webm`, `rtsp://`, and web/embed links play reliably.
 */
object StreamPlaybackSupport {

    // Stores real runtime format hints discovered from HTTP Content-Type / redirect URLs / response magic bytes
    private val runtimeHintMap = ConcurrentHashMap<String, String>()

    fun recordRuntimeHint(url: String, hintMimeType: String) {
        if (url.isNotBlank() && hintMimeType.isNotBlank()) {
            runtimeHintMap[url.trim()] = hintMimeType
        }
    }

    fun getRuntimeHint(url: String): String? = runtimeHintMap[url.trim()]

    fun clearRuntimeHint(url: String) {
        runtimeHintMap.remove(url.trim())
    }

    /**
     * Cleans whitespace, prepends missing scheme for bare host/path URLs, and normalizes duplicate path slashes
     * without corrupting query tokens or base64 parameters.
     */
    fun normalizeStreamUrl(rawUrl: String): String {
        var url = rawUrl.trim()
            .replace("\r", "")
            .replace("\n", "")
            .trim('"', '\'')
        if (url.isBlank()) return ""

        if (url.startsWith("//")) {
            url = "https:$url"
        } else if (!url.startsWith("http://", ignoreCase = true) &&
            !url.startsWith("https://", ignoreCase = true) &&
            !url.startsWith("rtsp://", ignoreCase = true) &&
            !url.startsWith("rtmp://", ignoreCase = true) &&
            !url.startsWith("udp://", ignoreCase = true) &&
            !url.startsWith("rtp://", ignoreCase = true) &&
            !url.startsWith("mms://", ignoreCase = true) &&
            !url.startsWith("file://", ignoreCase = true) &&
            !url.startsWith("content://", ignoreCase = true) &&
            !url.startsWith("asset://", ignoreCase = true) &&
            !url.startsWith("/")
        ) {
            if (url.contains(".") && (url.contains("/") || url.contains(":"))) {
                url = "http://$url"
            }
        }

        val queryIdx = url.indexOf('?')
        val pathPart = if (queryIdx != -1) url.substring(0, queryIdx) else url
        val queryPart = if (queryIdx != -1) url.substring(queryIdx) else ""

        val schemeMatch = Regex("^[a-zA-Z][a-zA-Z0-9+.-]*://").find(pathPart)
        val scheme = schemeMatch?.value.orEmpty()
        val rest = if (scheme.isNotEmpty()) pathPart.substring(scheme.length) else pathPart
        val normalizedRest = if (scheme.isNotEmpty()) rest.replace(Regex("/+"), "/") else rest
        return scheme + normalizedRest + queryPart
    }

    /**
     * Extracts user:pass embedded credentials (e.g. http://user:pass@host/live/...) into a Basic Authorization header.
     */
    fun extractBasicAuthHeader(url: String): String? {
        return try {
            val userInfo = Uri.parse(url).userInfo
            if (!userInfo.isNullOrBlank() && userInfo.contains(":")) {
                val encoded = Base64.encodeToString(userInfo.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
                "Basic $encoded"
            } else {
                null
            }
        } catch (_: Exception) {
            null
        }
    }

    fun isExternalOnlyProtocol(url: String): Boolean {
        val lower = url.trim().lowercase()
        return lower.startsWith("rtmp://") ||
            lower.startsWith("rtmps://") ||
            lower.startsWith("udp://") ||
            lower.startsWith("rtp://") ||
            lower.startsWith("mms://") ||
            lower.startsWith("mmsh://")
    }

    fun isDirectVodOrWebLink(url: String, title: String = ""): Boolean {
        val lowerUrl = url.lowercase().trim()
        return lowerUrl.contains("movielinkbd") ||
            lowerUrl.contains("mlbd") ||
            lowerUrl.contains("fsldownload") ||
            lowerUrl.contains("faststream") ||
            (lowerUrl.contains("/p/") && (lowerUrl.contains("fsldownload") || lowerUrl.contains("faststream"))) ||
            (lowerUrl.contains("/media/") && lowerUrl.contains("token=")) ||
            lowerUrl.contains("pixeldrain") ||
            lowerUrl.contains("pixeldra.in") ||
            lowerUrl.contains("drive.google") ||
            lowerUrl.contains("dropbox") ||
            lowerUrl.contains("mediafire") ||
            lowerUrl.contains("youtube.com") ||
            lowerUrl.contains("youtu.be") ||
            lowerUrl.contains("facebook.com") ||
            lowerUrl.contains("fb.watch") ||
            lowerUrl.contains("fb.com") ||
            lowerUrl.contains("dailymotion.com") ||
            lowerUrl.contains("dai.ly") ||
            lowerUrl.contains("vimeo.com") ||
            lowerUrl.contains("ok.ru") ||
            lowerUrl.contains("2embed") ||
            lowerUrl.contains("vidsrc") ||
            lowerUrl.contains("streamtape") ||
            lowerUrl.contains("dood") ||
            lowerUrl.contains("mixdrop") ||
            lowerUrl.contains(".mp4") ||
            lowerUrl.contains(".m4v") ||
            lowerUrl.contains(".mkv") ||
            lowerUrl.contains(".webm") ||
            lowerUrl.contains(".avi") ||
            lowerUrl.contains(".mov") ||
            lowerUrl.contains(".flv") ||
            lowerUrl.contains(".3gp") ||
            lowerUrl.contains(".mp3") ||
            lowerUrl.contains(".m4a") ||
            lowerUrl.contains(".html") ||
            lowerUrl.contains("/movie/") ||
            lowerUrl.contains("/series/") ||
            title.contains("movie", ignoreCase = true) ||
            title.contains("মুভি", ignoreCase = true) ||
            title.contains("film", ignoreCase = true)
    }

    /**
     * Classifies stream URL and runtime hints into exact ExoPlayer container/protocol parameters.
     */
    fun classifyStream(
        cleanUrl: String,
        mediaType: MediaType,
        isLiveFlag: Boolean,
        isSeriesFlag: Boolean,
        category: String = "",
        drmManifestType: String? = null,
        itemManifestType: String? = null,
        isMovieLinkBd: Boolean = false,
        isToffee: Boolean = false,
        mimeOverride: String? = null
    ): StreamClassification {
        val lower = cleanUrl.lowercase().trim()
        val pathBeforeQuery = lower.substringBefore('?')
        val runtimeHint = mimeOverride ?: getRuntimeHint(cleanUrl)

        val isStalkerStream = lower.contains("/play/live.php") ||
            (lower.contains("mac=") && lower.contains("stream=")) ||
            lower.contains("tivi-one")

        val isXtreamStream = lower.contains("/live/") ||
            lower.contains("/movie/") ||
            lower.contains("/series/") ||
            lower.contains("fixtv123") ||
            lower.contains("rgkkw") ||
            lower.contains("growthtry") ||
            lower.contains("my-king") ||
            lower.contains("zerotv") ||
            isStalkerStream

        if (isExternalOnlyProtocol(lower)) {
            return StreamClassification(
                kind = StreamKind.EXTERNAL_PROTOCOL,
                exoMimeType = null,
                isLiveStream = true,
                isTsStream = false,
                isXtreamStream = false,
                isStalkerStream = false
            )
        }

        if (lower.startsWith("rtsp://")) {
            return StreamClassification(
                kind = StreamKind.RTSP,
                exoMimeType = MimeTypes.APPLICATION_RTSP,
                isLiveStream = true,
                isTsStream = false,
                isXtreamStream = false,
                isStalkerStream = false
            )
        }

        val isVodContainer = pathBeforeQuery.endsWith(".mp4") ||
            pathBeforeQuery.endsWith(".m4v") ||
            pathBeforeQuery.endsWith(".mkv") ||
            pathBeforeQuery.endsWith(".webm") ||
            pathBeforeQuery.endsWith(".avi") ||
            pathBeforeQuery.endsWith(".mov") ||
            pathBeforeQuery.endsWith(".flv") ||
            pathBeforeQuery.endsWith(".3gp") ||
            pathBeforeQuery.endsWith(".mp3") ||
            pathBeforeQuery.endsWith(".m4a") ||
            pathBeforeQuery.endsWith(".aac") ||
            pathBeforeQuery.endsWith(".ogg") ||
            pathBeforeQuery.endsWith(".flac") ||
            pathBeforeQuery.endsWith(".wav")

        val isMovieFormat = isVodContainer ||
            lower.contains("/movie/") ||
            lower.contains("/series/") ||
            mediaType == MediaType.MOVIE ||
            mediaType == MediaType.SERIES ||
            isSeriesFlag ||
            isMovieLinkBd

        val isMpd = runtimeHint == MimeTypes.APPLICATION_MPD ||
            pathBeforeQuery.endsWith(".mpd") ||
            lower.contains(".mpd?") ||
            lower.contains("/dash/") ||
            lower.contains("format=mpd") ||
            lower.contains("type=dash") ||
            drmManifestType.equals("mpd", ignoreCase = true) ||
            itemManifestType.equals("mpd", ignoreCase = true)

        val isHlsFromUrl = pathBeforeQuery.endsWith(".m3u8") ||
            lower.contains(".m3u8?") ||
            lower.contains("/hls/") ||
            lower.contains("output=m3u8") ||
            lower.contains("output=hls") ||
            lower.contains("extension=m3u8") ||
            lower.contains("format=hls") ||
            drmManifestType.equals("hls", ignoreCase = true) ||
            itemManifestType.equals("hls", ignoreCase = true) ||
            isToffee

        val isExplicitTs = !isMovieFormat && (
            pathBeforeQuery.endsWith(".ts") ||
                pathBeforeQuery.endsWith(".m2ts") ||
                pathBeforeQuery.endsWith(".mts") ||
                lower.contains(".ts?") ||
                lower.contains("video/mp2t") ||
                lower.contains("output=ts") ||
                lower.contains("extension=ts") ||
                lower.contains("type=ts") ||
                (isStalkerStream && !isHlsFromUrl) ||
                (isXtreamStream && !isHlsFromUrl && !isMpd)
            )

        // Respect runtime hint if HTTP response/magic bytes contradicted the URL extension (e.g. .ts returning #EXTM3U)
        val effectiveKind: StreamKind
        val effectiveMime: String?
        when {
            runtimeHint == MimeTypes.APPLICATION_M3U8 -> {
                effectiveKind = StreamKind.HLS
                effectiveMime = MimeTypes.APPLICATION_M3U8
            }
            runtimeHint == MimeTypes.APPLICATION_MPD || isMpd -> {
                effectiveKind = StreamKind.DASH
                effectiveMime = MimeTypes.APPLICATION_MPD
            }
            runtimeHint == MimeTypes.VIDEO_MP2T -> {
                effectiveKind = StreamKind.MPEG_TS
                effectiveMime = MimeTypes.VIDEO_MP2T
            }
            runtimeHint == "AUTO_SNIFF" -> {
                effectiveKind = StreamKind.PROGRESSIVE
                effectiveMime = null
            }
            isHlsFromUrl -> {
                effectiveKind = StreamKind.HLS
                effectiveMime = MimeTypes.APPLICATION_M3U8
            }
            isExplicitTs -> {
                effectiveKind = StreamKind.MPEG_TS
                // Leaving MIME null allows DefaultMediaSourceFactory to check redirected URI/headers, OR explicit VIDEO_MP2T when path clearly ends in .ts.
                // Notice: setting VIDEO_MP2T tells DefaultMediaSourceFactory to use ProgressiveMediaSource; if server actually sends #EXTM3U, TsSyncDataSource records APPLICATION_M3U8 hint!
                effectiveMime = MimeTypes.VIDEO_MP2T
            }
            pathBeforeQuery.endsWith(".mp4") || (!lower.contains(".m3u8") && lower.contains(".mp4?")) -> {
                effectiveKind = StreamKind.PROGRESSIVE
                effectiveMime = MimeTypes.VIDEO_MP4
            }
            pathBeforeQuery.endsWith(".mkv") || isMovieLinkBd -> {
                effectiveKind = StreamKind.PROGRESSIVE
                effectiveMime = MimeTypes.VIDEO_MATROSKA
            }
            pathBeforeQuery.endsWith(".webm") -> {
                effectiveKind = StreamKind.PROGRESSIVE
                effectiveMime = MimeTypes.VIDEO_WEBM
            }
            isVodContainer -> {
                // FLV, AVI, MOV, 3GP, MP3, AAC, OGG, FLAC, WAV -> let DefaultExtractorsFactory auto-sniff natively
                effectiveKind = StreamKind.PROGRESSIVE
                effectiveMime = null
            }
            else -> {
                // Extensionless HTTP live or VOD URL: let DefaultExtractorsFactory auto-sniff first (if HLS, TsSyncDataSource/MediaStreamingClient detects #EXTM3U)
                effectiveKind = StreamKind.PROGRESSIVE
                effectiveMime = null
            }
        }

        val isLive = !isMovieFormat && (
            isLiveFlag ||
                mediaType == MediaType.LIVE_TV ||
                mediaType == MediaType.LIVE_EVENT ||
                effectiveKind == StreamKind.MPEG_TS ||
                effectiveKind == StreamKind.HLS ||
                effectiveKind == StreamKind.RTSP ||
                isXtreamStream ||
                category.contains("live", ignoreCase = true)
            )

        return StreamClassification(
            kind = effectiveKind,
            exoMimeType = effectiveMime,
            isLiveStream = isLive,
            isTsStream = effectiveKind == StreamKind.MPEG_TS,
            isXtreamStream = isXtreamStream,
            isStalkerStream = isStalkerStream
        )
    }

    /**
     * Scans a raw buffer for 3 consecutive MPEG-TS sync bytes (0x47) separated by 188-byte packet stride.
     * Returns the aligned start offset, or -1 if no valid MPEG-TS packet sequence is found.
     */
    fun findTsSyncOffset(data: ByteArray, length: Int): Int {
        val packetSize = 188
        val requiredMatches = 3
        val minNeeded = packetSize * (requiredMatches - 1) + 1
        if (length < minNeeded) return -1

        val maxStart = length - minNeeded
        for (i in 0..maxStart) {
            if ((data[i].toInt() and 0xFF) == 0x47) {
                var matched = true
                for (k in 1 until requiredMatches) {
                    if ((data[i + k * packetSize].toInt() and 0xFF) != 0x47) {
                        matched = false
                        break
                    }
                }
                if (matched) return i
            }
        }
        return -1
    }

    /**
     * Inspects the leading bytes of an HTTP stream response to detect disguised HLS/DASH playlists
     * served from `.ts` or extensionless IPTV endpoints.
     */
    fun sniffMagicMimeType(data: ByteArray, length: Int): String? {
        if (length <= 0) return null
        val inspectLen = length.coerceAtMost(256)
        val headerText = try {
            String(data, 0, inspectLen, Charsets.UTF_8).trimStart('\uFEFF', ' ', '\t', '\r', '\n')
        } catch (_: Exception) {
            ""
        }
        return when {
            headerText.contains("#EXT-X-TARGETDURATION", ignoreCase = true) ||
                headerText.contains("#EXT-X-STREAM-INF", ignoreCase = true) ||
                headerText.contains("#EXT-X-VERSION", ignoreCase = true) ||
                (headerText.startsWith("#EXTM3U", ignoreCase = true) && !headerText.contains("tvg-", ignoreCase = true)) -> MimeTypes.APPLICATION_M3U8
            headerText.startsWith("<MPD", ignoreCase = true) ||
                (headerText.startsWith("<?xml", ignoreCase = true) && headerText.contains("<MPD", ignoreCase = true)) -> MimeTypes.APPLICATION_MPD
            else -> null
        }
    }

    /**
     * Builds an ordered list of User-Agent candidates tailored to the stream type for automatic retry resilience.
     */
    fun buildUserAgentFallbackList(
        url: String,
        extractedUa: String?,
        isStalkerStream: Boolean,
        isTapmad: Boolean,
        isMovieLinkBd: Boolean,
        isToffee: Boolean,
        isTsOrXtream: Boolean
    ): List<String> {
        val primaryUa = when {
            isStalkerStream -> "Mozilla/5.0 (QtEmbedded; U; Linux; C) AppleWebKit/533.3 (KHTML, like Gecko) MAG200 stbapp ver: 2 rev: 250 Safari/533.3"
            isTapmad -> MediaStreamingClient.USER_AGENT_NETWORK_STREAM
            !extractedUa.isNullOrBlank() -> extractedUa
            isToffee -> "Toffee (Linux;Android 14)"
            isMovieLinkBd -> "Mozilla/5.0 (Linux; Android 14; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36"
            isTsOrXtream -> MediaStreamingClient.USER_AGENT_IPTV_SMARTERS
            else -> "Mozilla/5.0 (Linux; Android 14; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36"
        }

        return when {
            isStalkerStream -> listOf(
                primaryUa,
                MediaStreamingClient.USER_AGENT_VLC,
                "TiviMate/4.7.0 (Android TV)",
                MediaStreamingClient.USER_AGENT_LAVF,
                MediaStreamingClient.USER_AGENT_IPTV_SMARTERS
            )
            isTapmad -> listOf(
                primaryUa,
                "ExoPlayerLib/2.19.1 (Linux; Android 14; Mobile)",
                MediaStreamingClient.USER_AGENT_VLC,
                "Mozilla/5.0 (Linux; Android 14; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36",
                MediaStreamingClient.USER_AGENT_IPTV_SMARTERS
            )
            isMovieLinkBd -> listOf(
                primaryUa,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36",
                "Mozilla/5.0 (Linux; Android 14; Pixel 8 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36"
            )
            else -> listOf(
                primaryUa,
                MediaStreamingClient.USER_AGENT_VLC,
                MediaStreamingClient.USER_AGENT_LAVF,
                MediaStreamingClient.USER_AGENT_IPTV_SMARTERS,
                MediaStreamingClient.USER_AGENT_TELEVIZO,
                "TiviMate/4.7.0 (Android TV)",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
            )
        }.distinct()
    }
}

/**
 * DataSource wrapper for live MPEG-TS streams that transparently aligns unaligned TCP streams
 * onto an exact `0x47` 188-byte sync boundary and records `#EXTM3U` / `<MPD` magic hints if the
 * server disguised a playlist behind a `.ts` or extensionless URL.
 */
@UnstableApi
class TsSyncDataSource(
    private val upstream: DataSource,
    private val enableAlignment: Boolean = true
) : DataSource {

    private var activeUrl: String = ""
    private var shouldAlignThisOpen = false
    private var inspected = false
    private var prefetchBuf: ByteArray? = null
    private var prefetchLen = 0
    private var prefetchPos = 0

    override fun addTransferListener(transferListener: TransferListener) {
        upstream.addTransferListener(transferListener)
    }

    @Throws(IOException::class)
    override fun open(dataSpec: DataSpec): Long {
        activeUrl = dataSpec.uri.toString()
        inspected = false
        prefetchBuf = null
        prefetchLen = 0
        prefetchPos = 0

        val lowerPath = activeUrl.substringBefore('?').lowercase()
        val isProgressiveNonTs = lowerPath.endsWith(".mp4") ||
            lowerPath.endsWith(".mkv") ||
            lowerPath.endsWith(".webm") ||
            lowerPath.endsWith(".avi") ||
            lowerPath.endsWith(".mov") ||
            lowerPath.endsWith(".mp3") ||
            lowerPath.endsWith(".m4a") ||
            lowerPath.endsWith(".m3u8") ||
            lowerPath.endsWith(".mpd")

        shouldAlignThisOpen = enableAlignment &&
            dataSpec.position == 0L &&
            !isProgressiveNonTs &&
            (activeUrl.startsWith("http://", ignoreCase = true) || activeUrl.startsWith("https://", ignoreCase = true))

        return upstream.open(dataSpec)
    }

    @Throws(IOException::class)
    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        if (length == 0) return 0

        if (shouldAlignThisOpen && !inspected) {
            inspected = true
            // Read initial chunk (up to 16 KB) on the exact same HTTP socket to align 0x47 sync byte or detect #EXTM3U
            val probeCap = 16 * 1024
            val tmp = ByteArray(probeCap)
            var total = 0
            while (total < 4096) {
                val r = upstream.read(tmp, total, probeCap - total)
                if (r <= 0) break
                total += r
                // Stop reading immediately if TS sync is confirmed or magic playlist header detected
                if (total >= 377 && StreamPlaybackSupport.findTsSyncOffset(tmp, total) >= 0) {
                    break
                }
                if (total >= 16 && StreamPlaybackSupport.sniffMagicMimeType(tmp, total) != null) {
                    break
                }
            }

            if (total > 0) {
                val sniffedMime = StreamPlaybackSupport.sniffMagicMimeType(tmp, total)
                var startOffset = 0
                if (sniffedMime != null) {
                    StreamPlaybackSupport.recordRuntimeHint(activeUrl, sniffedMime)
                    val redirected = upstream.uri?.toString()
                    if (!redirected.isNullOrBlank() && redirected != activeUrl) {
                        StreamPlaybackSupport.recordRuntimeHint(redirected, sniffedMime)
                    }
                } else {
                    val syncOffset = StreamPlaybackSupport.findTsSyncOffset(tmp, total)
                    if (syncOffset > 0) {
                        startOffset = syncOffset
                    }
                }
                prefetchBuf = tmp
                prefetchLen = total
                prefetchPos = startOffset
            }
        }

        val buf = prefetchBuf
        if (buf != null && prefetchPos < prefetchLen) {
            val available = prefetchLen - prefetchPos
            val toCopy = available.coerceAtMost(length)
            System.arraycopy(buf, prefetchPos, buffer, offset, toCopy)
            prefetchPos += toCopy
            if (prefetchPos >= prefetchLen) {
                prefetchBuf = null
            }
            return toCopy
        }

        return upstream.read(buffer, offset, length)
    }

    override fun getUri(): Uri? = upstream.uri

    override fun getResponseHeaders(): Map<String, List<String>> = upstream.responseHeaders

    @Throws(IOException::class)
    override fun close() {
        prefetchBuf = null
        prefetchLen = 0
        prefetchPos = 0
        upstream.close()
    }

    class Factory(
        private val delegate: DataSource.Factory,
        private val enableAlignment: Boolean = true
    ) : DataSource.Factory {
        override fun createDataSource(): DataSource {
            return TsSyncDataSource(delegate.createDataSource(), enableAlignment)
        }
    }
}

package com.example.util

import android.content.Context
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.TransferListener
import androidx.media3.datasource.okhttp.OkHttpDataSource
import okhttp3.ConnectionPool
import okhttp3.Dns
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.net.InetAddress
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Universal High-Resilience Streaming Client for Media3 ExoPlayer.
 * Incorporates streaming network architecture and resilience patterns from:
 * 1. Televizo (com.ottplay.ottplay) - Multi-header routing, smart DNS fallback, Xtream codes bypass
 * 2. Network Stream (Video) Player (com.genuine.leone) - Edge redirect healing, protocol sniffing, socket keep-alive
 * 3. VLC for Android (org.videolan.vlc) - Universal codec negotiation, PTS discontinuity tolerance, SSL leniency
 */
object MediaStreamingClient {

    val USER_AGENT_TELEVIZO = "Televizo/1.9.3 (Linux; Android 14)"
    val USER_AGENT_NETWORK_STREAM = "NetworkStreamPlayer/1.0 (Linux; Android 14; Mobile)"
    val USER_AGENT_VLC = "VLC/3.0.18 LibVLC/3.0.18"
    val USER_AGENT_IPTV_SMARTERS = "IPTVSmartersPro/3.1.5.1 (Linux; Android 14)"
    val USER_AGENT_LAVF = "Lavf/58.76.100"

    private val fallbackUserAgents = listOf(
        USER_AGENT_TELEVIZO,
        USER_AGENT_NETWORK_STREAM,
        USER_AGENT_VLC,
        USER_AGENT_IPTV_SMARTERS,
        USER_AGENT_LAVF
    )

    private val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    )

    private val sslSocketFactory by lazy {
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustAllCerts, SecureRandom())
        }
        sslContext.socketFactory
    }

    // Cache of proven healthy edge IPs for IPTV CDN domains (such as growthtry.com clusters)
    private val healthyHostIps = ConcurrentHashMap<String, List<String>>().apply {
        put("growthtry.com", listOf("43.250.127.165", "172.110.220.189"))
    }

    /**
     * Smart DNS resolver:
     * 1. Resolves via system DNS.
     * 2. For growthtry.com / Xtream CDN clusters, filters out bad/dead nodes (e.g. 172.110.220.190 which returns 404 Apache)
     *    and guarantees active streaming nodes (43.250.127.165, 172.110.220.189) are returned.
     * 3. Seamlessly falls back to system lookup if all else fails.
     */
    val smartDns: Dns = object : Dns {
        override fun lookup(hostname: String): List<InetAddress> {
            val isGrowthtry = hostname.contains("growthtry.com", ignoreCase = true)

            val resolved = mutableListOf<InetAddress>()
            try {
                val system = Dns.SYSTEM.lookup(hostname)
                if (isGrowthtry) {
                    // Filter out 172.110.220.190 which is a misconfigured node returning 404
                    resolved.addAll(system.filterNot { it.hostAddress == "172.110.220.190" })
                } else {
                    resolved.addAll(system)
                }
            } catch (_: Exception) {}

            if (isGrowthtry) {
                // Ensure known healthy live nginx streaming IPs are in the list
                val healthy = healthyHostIps["growthtry.com"] ?: emptyList()
                for (ip in healthy) {
                    try {
                        val addr = InetAddress.getByName(ip)
                        if (!resolved.contains(addr)) {
                            resolved.add(0, addr) // Prioritize healthy node
                        }
                    } catch (_: Exception) {}
                }
            }

            if (resolved.isNotEmpty()) {
                return resolved
            }

            return Dns.SYSTEM.lookup(hostname)
        }
    }

    private val cookieStore = ConcurrentHashMap<String, List<okhttp3.Cookie>>()

    private val sessionCookieJar = object : okhttp3.CookieJar {
        override fun saveFromResponse(url: okhttp3.HttpUrl, cookies: List<okhttp3.Cookie>) {
            if (cookies.isNotEmpty()) {
                val valid = cookies.filter { it.expiresAt > System.currentTimeMillis() }
                if (valid.isNotEmpty()) {
                    cookieStore[url.host] = valid
                }
            }
        }

        override fun loadForRequest(url: okhttp3.HttpUrl): List<okhttp3.Cookie> {
            val now = System.currentTimeMillis()
            return cookieStore[url.host]?.filter { it.expiresAt > now }.orEmpty()
        }
    }

    /**
     * Auto-healing interceptor:
     * 1. Ensures Accept-Encoding: identity on streaming URLs so reverse proxies never gzip-buffer live TS/media streams.
     * 2. Records HTTP Content-Type / final redirected URL hints so ExoPlayer handles .ts -> .m3u8 redirects cleanly.
     * 3. Handles growthtry 404 edge node recovery and retries blocked User-Agents cleanly.
     */
    private val streamHealingInterceptor = Interceptor { chain ->
        val rawRequest = chain.request()
        val originalRequest = if (rawRequest.header("Accept-Encoding") == null) {
            rawRequest.newBuilder().header("Accept-Encoding", "identity").build()
        } else {
            rawRequest
        }
        val reqUrlStr = originalRequest.url.toString()
        val reqUrlLower = reqUrlStr.lowercase()
        var response = chain.proceed(originalRequest)

        // 1. Edge Node Healing (Growthtry / Xtream unrouted 404)
        if (response.code == 404 && originalRequest.url.host.contains("growthtry.com", ignoreCase = true)) {
            response.close()
            val fallbackUrl = originalRequest.url.newBuilder()
                .host("43.250.127.165")
                .build()
            val fallbackRequest = originalRequest.newBuilder()
                .url(fallbackUrl)
                .header("Host", originalRequest.url.host)
                .build()
            return@Interceptor chain.proceed(fallbackRequest)
        }

        // 2. UA Rejection Auto-Retry (on IPTV live TS, HLS, DASH, Stalker, VOD streams)
        val isStreamingUrl = reqUrlLower.contains("/live/") ||
            reqUrlLower.contains("/movie/") ||
            reqUrlLower.contains("/series/") ||
            reqUrlLower.contains("/hls/") ||
            reqUrlLower.contains("/stream") ||
            reqUrlLower.contains("/play/live.php") ||
            reqUrlLower.contains("mac=") ||
            reqUrlLower.contains(".m3u8") ||
            reqUrlLower.contains(".mpd") ||
            reqUrlLower.contains(".ts") ||
            reqUrlLower.contains(".mp4") ||
            reqUrlLower.contains(".mkv") ||
            reqUrlLower.contains(".flv") ||
            reqUrlLower.contains("output=") ||
            reqUrlLower.contains("extension=")

        val shouldRetryAuthOrBlock = response.code in listOf(400, 401, 403, 405, 406, 407, 429, 451, 461, 502, 503)
        val shouldRetryNotFoundOnce = response.code == 404

        if ((shouldRetryAuthOrBlock || shouldRetryNotFoundOnce) && isStreamingUrl) {
            val currentUa = originalRequest.header("User-Agent").orEmpty()
            val stalkerUa = "Mozilla/5.0 (QtEmbedded; U; Linux; C) AppleWebKit/533.3 (KHTML, like Gecko) MAG200 stbapp ver: 2 rev: 250 Safari/533.3"
            val candidateUas = if (shouldRetryNotFoundOnce) {
                listOf(USER_AGENT_VLC, USER_AGENT_LAVF)
            } else {
                listOf(stalkerUa) + fallbackUserAgents + listOf("TiviMate/4.7.0 (Android TV)")
            }

            for (altUa in candidateUas) {
                if (!altUa.equals(currentUa, ignoreCase = true)) {
                    response.close()
                    val retryRequestBuilder = originalRequest.newBuilder()
                        .header("User-Agent", altUa)

                    // Stalker & CDN Proxy healing
                    if (reqUrlLower.contains("mac=") || reqUrlLower.contains("tivi-one")) {
                        retryRequestBuilder.header("X-User-Agent", "Model: MAG250; Link: Ethernet")
                        retryRequestBuilder.removeHeader("Referer")
                        retryRequestBuilder.removeHeader("Origin")
                        val mac = try { android.net.Uri.parse(reqUrlStr).getQueryParameter("mac") } catch (_: Exception) { null }
                        if (!mac.isNullOrBlank()) {
                            retryRequestBuilder.header("Cookie", "mac=$mac; stb_lang=en; timezone=Europe/Kiev;")
                        }
                    }

                    // Akamai / Cloudflare / Xtream panels often block unexpected browser Referer/Origin on direct streams
                    if (originalRequest.url.host.contains("akamaized.net", ignoreCase = true) ||
                        originalRequest.url.host.contains("workers.dev", ignoreCase = true) ||
                        reqUrlLower.contains("/live/") ||
                        reqUrlLower.contains(".ts")
                    ) {
                        retryRequestBuilder.removeHeader("Referer")
                        retryRequestBuilder.removeHeader("Origin")
                    }

                    val retryRequest = retryRequestBuilder.build()
                    val retryResp = chain.proceed(retryRequest)
                    if (retryResp.isSuccessful || retryResp.code in 200..308) {
                        recordFormatHintFromResponse(reqUrlStr, retryResp)
                        return@Interceptor retryResp
                    }
                    response = retryResp
                }
            }
        }

        if (response.isSuccessful) {
            recordFormatHintFromResponse(reqUrlStr, response)
        }
        response
    }

    private fun recordFormatHintFromResponse(originalUrl: String, response: okhttp3.Response) {
        try {
            val finalUrl = response.request.url.toString().lowercase()
            val contentType = response.header("Content-Type")?.lowercase().orEmpty()
            val hint = when {
                contentType.contains("mpegurl") ||
                    contentType.contains("vnd.apple") ||
                    finalUrl.substringBefore('?').endsWith(".m3u8") -> androidx.media3.common.MimeTypes.APPLICATION_M3U8
                contentType.contains("dash+xml") ||
                    finalUrl.substringBefore('?').endsWith(".mpd") -> androidx.media3.common.MimeTypes.APPLICATION_MPD
                contentType.contains("mp2t") -> androidx.media3.common.MimeTypes.VIDEO_MP2T
                else -> null
            }
            if (hint != null) {
                StreamPlaybackSupport.recordRuntimeHint(originalUrl, hint)
                StreamPlaybackSupport.recordRuntimeHint(response.request.url.toString(), hint)
            }
        } catch (_: Exception) {}
    }

    val streamingOkHttpClient: OkHttpClient by lazy {
        try {
            OkHttpClient.Builder()
                .dns(smartDns)
                .cookieJar(sessionCookieJar)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .callTimeout(0, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .addInterceptor(streamHealingInterceptor)
                .apply {
                    try {
                        sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                        hostnameVerifier { _, _ -> true }
                    } catch (_: Throwable) {}
                }
                .connectionPool(ConnectionPool(64, 5, TimeUnit.MINUTES))
                .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
                .build()
        } catch (_: Throwable) {
            try {
                OkHttpClient.Builder()
                    .cookieJar(sessionCookieJar)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(25, TimeUnit.SECONDS)
                    .followRedirects(true)
                    .retryOnConnectionFailure(true)
                    .build()
            } catch (_: Throwable) {
                OkHttpClient()
            }
        }
    }

    fun evictIdleConnections() {
        try {
            streamingOkHttpClient.connectionPool.evictAll()
        } catch (_: Exception) {}
    }

    /**
     * Creates an optimized DataSource.Factory delegating HTTP requests to OkHttp
     * and local files/assets/content to standard handlers, with optional mid-packet TS sync alignment.
     */
    fun createDataSourceFactory(
        context: Context,
        userAgent: String,
        headers: Map<String, String>,
        bandwidthMeter: TransferListener? = null,
        enableTsSyncAlignment: Boolean = true
    ): Pair<DataSource.Factory, DataSource.Factory> {
        return try {
            val okHttpDataSourceFactory = OkHttpDataSource.Factory(streamingOkHttpClient)
                .setUserAgent(userAgent)
                .setDefaultRequestProperties(headers)

            if (bandwidthMeter != null) {
                okHttpDataSourceFactory.setTransferListener(bandwidthMeter)
            }

            val baseDefaultFactory = DefaultDataSource.Factory(
                context,
                okHttpDataSourceFactory
            )
            val wrappedFactory: DataSource.Factory = if (enableTsSyncAlignment) {
                TsSyncDataSource.Factory(baseDefaultFactory, enableAlignment = true)
            } else {
                baseDefaultFactory
            }

            Pair(okHttpDataSourceFactory, wrappedFactory)
        } catch (e: Throwable) {
            android.util.Log.e("MediaStreamingClient", "Error building OkHttpDataSource.Factory, using standard DefaultHttpDataSource", e)
            val stdHttpFactory = androidx.media3.datasource.DefaultHttpDataSource.Factory()
                .setUserAgent(userAgent)
                .setDefaultRequestProperties(headers)
            if (bandwidthMeter != null) {
                stdHttpFactory.setTransferListener(bandwidthMeter)
            }
            val defaultFactory = DefaultDataSource.Factory(context, stdHttpFactory)
            val wrappedFactory: DataSource.Factory = if (enableTsSyncAlignment) {
                TsSyncDataSource.Factory(defaultFactory, enableAlignment = true)
            } else {
                defaultFactory
            }
            Pair(stdHttpFactory, wrappedFactory)
        }
    }
}

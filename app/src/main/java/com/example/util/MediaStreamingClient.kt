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
 * High-performance, low-latency singleton streaming client for Media3 ExoPlayer.
 * Reuses connection pools, avoids repeated SSL/TLS handshakes, provides resilient DNS,
 * and handles edge stream redirect resolution seamlessly for butter-smooth playback.
 */
object MediaStreamingClient {

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

    /**
     * Auto-healing interceptor:
     * If an IPTV redirect URL hits a 404 node (such as an un-routed edge server in growthtry.com),
     * automatically switches to the healthy edge IP node and retries instantly.
     */
    private val streamHealingInterceptor = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 404 && request.url.host.contains("growthtry.com", ignoreCase = true)) {
            response.close()
            val fallbackUrl = request.url.newBuilder()
                .host("43.250.127.165")
                .build()
            val fallbackRequest = request.newBuilder()
                .url(fallbackUrl)
                .header("Host", request.url.host)
                .build()
            return@Interceptor chain.proceed(fallbackRequest)
        }

        response
    }

    val streamingOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .dns(smartDns)
            .connectTimeout(12, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(12, TimeUnit.SECONDS)
            .callTimeout(0, TimeUnit.MILLISECONDS) // 0 = infinite duration for continuous live broadcasts
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .addInterceptor(streamHealingInterceptor)
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .connectionPool(ConnectionPool(16, 5, TimeUnit.MINUTES))
            .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
            .build()
    }

    /**
     * Creates an optimized DataSource.Factory delegating HTTP requests to OkHttp
     * and local files/assets/content to standard handlers.
     */
    fun createDataSourceFactory(
        context: Context,
        userAgent: String,
        headers: Map<String, String>,
        bandwidthMeter: TransferListener? = null
    ): Pair<OkHttpDataSource.Factory, DataSource.Factory> {
        val okHttpDataSourceFactory = OkHttpDataSource.Factory(streamingOkHttpClient)
            .setUserAgent(userAgent)
            .setDefaultRequestProperties(headers)

        if (bandwidthMeter != null) {
            okHttpDataSourceFactory.setTransferListener(bandwidthMeter)
        }

        val defaultDataSourceFactory = DefaultDataSource.Factory(
            context,
            okHttpDataSourceFactory
        )

        return Pair(okHttpDataSourceFactory, defaultDataSourceFactory)
    }
}

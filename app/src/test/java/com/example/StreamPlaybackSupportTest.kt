package com.example

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.BaseDataSource
import androidx.media3.datasource.DataSpec
import com.example.model.MediaType
import com.example.player.StreamExtractor
import com.example.util.DrmHelper
import com.example.util.MediaStreamingClient
import com.example.util.StreamKind
import com.example.util.StreamPlaybackSupport
import com.example.util.TsSyncDataSource
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class StreamPlaybackSupportTest {

    private val testUrls = listOf(
        "http://rgkkw.live/live/1Aoen7elp5/IgMJ60tmAa/151.ts",
        "http://rgkkw.live/live/1Aoen7elp5/IgMJ60tmAa/999.ts",
        "http://bangladesh-tv.com/live/ch1.ts"
    )

    @After
    fun cleanupHints() {
        testUrls.forEach { StreamPlaybackSupport.clearRuntimeHint(it) }
    }

    @Test
    fun `rgkkw xtream ts url is classified as MPEG_TS live stream with IPTV UA cascade`() {
        val url = "http://rgkkw.live/live/1Aoen7elp5/IgMJ60tmAa/151.ts"
        assertEquals(url, StreamPlaybackSupport.normalizeStreamUrl(url))

        val cls = StreamPlaybackSupport.classifyStream(
            cleanUrl = url,
            mediaType = MediaType.LIVE_TV,
            isLiveFlag = true,
            isSeriesFlag = false
        )
        assertEquals(StreamKind.MPEG_TS, cls.kind)
        assertEquals(MimeTypes.VIDEO_MP2T, cls.exoMimeType)
        assertTrue(cls.isLiveStream)
        assertTrue(cls.isTsStream)
        assertTrue(cls.isXtreamStream)

        val uas = StreamPlaybackSupport.buildUserAgentFallbackList(
            url = url,
            extractedUa = null,
            isStalkerStream = cls.isStalkerStream,
            isTapmad = false,
            isMovieLinkBd = false,
            isToffee = false,
            isTsOrXtream = true
        )
        assertEquals(MediaStreamingClient.USER_AGENT_IPTV_SMARTERS, uas.first())
        assertTrue(uas.contains(MediaStreamingClient.USER_AGENT_VLC))
        assertTrue(uas.contains(MediaStreamingClient.USER_AGENT_LAVF))
    }

    @Test
    fun `normalizeStreamUrl handles bare host paths without corrupting query tokens`() {
        assertEquals(
            "http://rgkkw.live/live/1Aoen7elp5/IgMJ60tmAa/151.ts",
            StreamPlaybackSupport.normalizeStreamUrl("  rgkkw.live//live///1Aoen7elp5/IgMJ60tmAa/151.ts  ")
        )
        assertEquals(
            "https://cdn.example.com/hls/master.m3u8?token=abc//123",
            StreamPlaybackSupport.normalizeStreamUrl("//cdn.example.com//hls/master.m3u8?token=abc//123")
        )
    }

    @Test
    fun `basic auth credentials in stream url produce Authorization Basic header`() {
        val header = StreamPlaybackSupport.extractBasicAuthHeader("http://myuser:mypass@stream.example.com/live/ch1.ts")
        assertNotNull(header)
        assertTrue(header!!.startsWith("Basic "))
        assertNull(StreamPlaybackSupport.extractBasicAuthHeader("http://rgkkw.live/live/1Aoen7elp5/IgMJ60tmAa/151.ts"))
    }

    @Test
    fun `domain containing bangladesh is not misclassified as DASH`() {
        val bangladeshTs = "http://bangladesh-tv.com/live/ch1.ts"
        val cls = StreamPlaybackSupport.classifyStream(
            cleanUrl = bangladeshTs,
            mediaType = MediaType.LIVE_TV,
            isLiveFlag = true,
            isSeriesFlag = false
        )
        assertEquals(StreamKind.MPEG_TS, cls.kind)
        assertEquals(MimeTypes.VIDEO_MP2T, cls.exoMimeType)

        val parsed = DrmHelper.extractStreamInfo("$bangladeshTs|User-Agent=VLC/3.0")
        assertNull(parsed.drmConfig?.manifestType)
        assertEquals("VLC/3.0", parsed.headers["User-Agent"])
    }

    @Test
    fun `universal link formats classify accurately across RTSP HLS DASH VOD Web and External`() {
        assertEquals(
            StreamKind.RTSP,
            StreamPlaybackSupport.classifyStream("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov", MediaType.LIVE_TV, true, false).kind
        )
        assertEquals(
            StreamKind.HLS,
            StreamPlaybackSupport.classifyStream("https://example.com/live/master.m3u8", MediaType.LIVE_TV, true, false).kind
        )
        assertEquals(
            StreamKind.DASH,
            StreamPlaybackSupport.classifyStream("https://example.com/live/manifest.mpd", MediaType.LIVE_TV, true, false).kind
        )
        val mkvCls = StreamPlaybackSupport.classifyStream("https://example.com/movies/sample.mkv", MediaType.MOVIE, false, false)
        assertEquals(StreamKind.PROGRESSIVE, mkvCls.kind)
        assertEquals(MimeTypes.VIDEO_MATROSKA, mkvCls.exoMimeType)
        assertFalse(mkvCls.isLiveStream)

        val flvCls = StreamPlaybackSupport.classifyStream("https://example.com/clip.flv", MediaType.MOVIE, false, false)
        assertEquals(StreamKind.PROGRESSIVE, flvCls.kind)
        assertNull(flvCls.exoMimeType)

        assertTrue(StreamPlaybackSupport.isExternalOnlyProtocol("rtmp://live.example.com/app/stream"))
        assertTrue(StreamPlaybackSupport.isExternalOnlyProtocol("udp://@239.0.0.1:1234"))
        assertEquals(
            StreamKind.EXTERNAL_PROTOCOL,
            StreamPlaybackSupport.classifyStream("rtmp://live.example.com/app/stream", MediaType.LIVE_TV, true, false).kind
        )

        assertTrue(StreamPlaybackSupport.isDirectVodOrWebLink("https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
        assertTrue(StreamPlaybackSupport.isDirectVodOrWebLink("https://www.mediafire.com/file/abc/movie.mkv/file"))
        assertTrue(StreamPlaybackSupport.isDirectVodOrWebLink("https://www.dropbox.com/s/xyz/video.mp4?dl=0"))
        assertTrue(StreamExtractor.isEmbedUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
        assertTrue(StreamExtractor.isEmbedUrl("https://www.mediafire.com/file/abc/movie.mkv/file"))
        assertFalse(StreamExtractor.isEmbedUrl("http://rgkkw.live/live/1Aoen7elp5/IgMJ60tmAa/151.ts"))
    }

    @OptIn(UnstableApi::class)
    @Test
    fun `TsSyncDataSource aligns mid-packet MPEG-TS bytes onto 0x47 packet boundary`() {
        val junkPrefix = 57
        val packetCount = 4
        val packetSize = 188
        val payload = ByteArray(junkPrefix + packetCount * packetSize) { 0x11 }
        for (p in 0 until packetCount) {
            payload[junkPrefix + p * packetSize] = 0x47
        }

        val fakeUpstream = FakeByteArrayDataSource(payload)
        val tsDataSource = TsSyncDataSource(fakeUpstream, enableAlignment = true)
        tsDataSource.open(DataSpec(Uri.parse("http://rgkkw.live/live/1Aoen7elp5/IgMJ60tmAa/151.ts")))

        val out = ByteArray(1024)
        val firstRead = tsDataSource.read(out, 0, out.size)
        tsDataSource.close()

        assertTrue(firstRead >= packetCount * packetSize)
        assertEquals(0x47, out[0].toInt() and 0xFF)
        assertEquals(0x47, out[packetSize].toInt() and 0xFF)
        assertEquals(0x47, out[packetSize * 2].toInt() and 0xFF)
    }

    @OptIn(UnstableApi::class)
    @Test
    fun `TsSyncDataSource records HLS runtime hint when ts endpoint serves EXTM3U playlist`() {
        val hlsManifest = "#EXTM3U\n#EXT-X-VERSION:3\n#EXT-X-TARGETDURATION:6\n#EXTINF:6.0,\nseg0.ts\n".toByteArray()
        val disguisedUrl = "http://rgkkw.live/live/1Aoen7elp5/IgMJ60tmAa/999.ts"
        val tsDataSource = TsSyncDataSource(FakeByteArrayDataSource(hlsManifest), enableAlignment = true)
        tsDataSource.open(DataSpec(Uri.parse(disguisedUrl)))

        val out = ByteArray(512)
        val readCount = tsDataSource.read(out, 0, out.size)
        tsDataSource.close()

        assertEquals(hlsManifest.size, readCount)
        assertEquals(MimeTypes.APPLICATION_M3U8, StreamPlaybackSupport.getRuntimeHint(disguisedUrl))

        val reclassified = StreamPlaybackSupport.classifyStream(
            cleanUrl = disguisedUrl,
            mediaType = MediaType.LIVE_TV,
            isLiveFlag = true,
            isSeriesFlag = false
        )
        assertEquals(StreamKind.HLS, reclassified.kind)
        assertEquals(MimeTypes.APPLICATION_M3U8, reclassified.exoMimeType)
    }

    @OptIn(UnstableApi::class)
    private class FakeByteArrayDataSource(private val bytes: ByteArray) : BaseDataSource(/* isNetwork = */ false) {
        private var uri: Uri? = null
        private var position = 0

        override fun open(dataSpec: DataSpec): Long {
            uri = dataSpec.uri
            position = dataSpec.position.toInt().coerceIn(0, bytes.size)
            transferInitializing(dataSpec)
            transferStarted(dataSpec)
            return C.LENGTH_UNSET.toLong()
        }

        override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
            if (length == 0) return 0
            if (position >= bytes.size) return C.RESULT_END_OF_INPUT
            val toCopy = (bytes.size - position).coerceAtMost(length)
            System.arraycopy(bytes, position, buffer, offset, toCopy)
            position += toCopy
            bytesTransferred(toCopy)
            return toCopy
        }

        override fun getUri(): Uri? = uri

        override fun close() {
            if (uri != null) {
                uri = null
                transferEnded()
            }
        }
    }
}

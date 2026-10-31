package com.example

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Looper
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.size.Precision
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.launch

class NafiTvApp : Application(), ImageLoaderFactory {

    private var customImageLoader: ImageLoader? = null

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 1. Register optimized Coil ImageLoader globally so all AsyncImage instances use low-RAM decode
        try {
            coil.Coil.setImageLoader(newImageLoader())
        } catch (e: Exception) {
            Log.w("NafiTvApp", "Coil image loader init error", e)
        }

        try {
            com.example.util.SportsInteractionManager.init(this)
        } catch (_: Exception) {}

        // Light background maintenance delayed by 4 seconds so UI loads instantaneously like butter
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            try {
                kotlinx.coroutines.delay(4000)
                com.example.util.StorageCleaner.cleanOnStartup(this@NafiTvApp)
            } catch (_: Exception) {}
        }

        // 2. Global crash protection: Intercepts decoder, GPU, network & memory crashes
        // preventing unexpected process termination on normal & low-spec (512MB RAM) devices
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("NafiTvApp", "Intercepted crash on thread: ${thread.name}", throwable)
            val msg = throwable.message ?: ""

            // Immediately clear memory caches to alleviate any memory pressure
            try {
                customImageLoader?.memoryCache?.clear()
                sharedOkHttpClient.connectionPool.evictAll()
            } catch (_: Throwable) {}

            if (thread != Looper.getMainLooper().thread) {
                // Background thread errors (network, ExoPlayer codecs, image loading, GC) -> suppress to keep app alive
                Log.w("NafiTvApp", "Suppressed background exception on ${thread.name}: ${throwable.javaClass.simpleName} - $msg")
            } else {
                Log.e("NafiTvApp", "Uncaught exception on UI thread: $msg", throwable)
                defaultHandler?.uncaughtException(thread, throwable)
            }
        }
    }

    fun isLowRamEnvironment(): Boolean {
        val actManager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager?.getMemoryInfo(memInfo)
        val totalRamMb = memInfo.totalMem / (1024 * 1024)
        val maxHeapMb = Runtime.getRuntime().maxMemory() / (1024 * 1024)
        return actManager?.isLowRamDevice == true || totalRamMb <= 1500 || maxHeapMb <= 192
    }

    override fun newImageLoader(): ImageLoader {
        return customImageLoader ?: synchronized(this) {
            customImageLoader ?: buildOptimizedImageLoader().also { customImageLoader = it }
        }
    }

    private fun buildOptimizedImageLoader(): ImageLoader {
        val isLowRam = isLowRamEnvironment()
        // On 512MB RAM phones, keep memory cache to 5% of heap to prevent LMK kills
        val memCachePercent = if (isLowRam) 0.05 else 0.15
        val diskCacheMaxBytes = if (isLowRam) 15L * 1024 * 1024 else 25L * 1024 * 1024

        return ImageLoader.Builder(this)
            .okHttpClient(sharedOkHttpClient)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(memCachePercent)
                    .strongReferencesEnabled(true)
                    .weakReferencesEnabled(!isLowRam)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(File(cacheDir, "nafitv_image_cache"))
                    .maxSizeBytes(diskCacheMaxBytes)
                    .build()
            }
            .bitmapConfig(Bitmap.Config.RGB_565) // 50% memory saving on channel logos & posters
            .allowHardware(!isLowRam) // GPU hardware bitmaps enabled for buttery 60/120fps scrolling on modern devices
            .allowRgb565(true)
            .crossfade(false) // Saves GPU compositing passes on low-RAM devices
            .precision(Precision.INEXACT) // Automatically downsamples posters and logos to target UI size
            .networkObserverEnabled(true)
            .respectCacheHeaders(false)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        try {
            customImageLoader?.memoryCache?.clear()
            if (level >= TRIM_MEMORY_MODERATE || isLowRamEnvironment()) {
                sharedOkHttpClient.connectionPool.evictAll()
                System.runFinalization()
                System.gc()
            }
        } catch (e: Exception) {
            Log.w("NafiTvApp", "Error trimming memory", e)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        try {
            customImageLoader?.memoryCache?.clear()
            sharedOkHttpClient.connectionPool.evictAll()
            System.runFinalization()
            System.gc()
        } catch (e: Exception) {
            Log.w("NafiTvApp", "Error on low memory", e)
        }
    }

    companion object {
        lateinit var instance: NafiTvApp
            private set

        val sharedOkHttpClient: OkHttpClient by lazy {
            val isLowRam = try {
                instance.isLowRamEnvironment()
            } catch (_: Exception) {
                false
            }
            val maxIdle = if (isLowRam) 4 else 8
            val keepAliveDuration = if (isLowRam) 30L else 120L

            val cacheDirectory = File(instance.cacheDir, "nafitv_http_cache")
            val cacheSize = if (isLowRam) 5L * 1024 * 1024 else 12L * 1024 * 1024

            OkHttpClient.Builder()
                .cache(okhttp3.Cache(cacheDirectory, cacheSize))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(maxIdle, keepAliveDuration, TimeUnit.SECONDS))
                .retryOnConnectionFailure(true)
                .build()
        }
    }
}

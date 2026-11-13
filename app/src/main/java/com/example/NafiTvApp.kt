package com.example

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Looper
import android.util.Log
import androidx.work.Configuration
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NafiTvApp : Application(), ImageLoaderFactory, Configuration.Provider {

    private var customImageLoader: ImageLoader? = null

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.ERROR)
            .build()

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 1. Install global crash protection FIRST so any background startup error is intercepted immediately
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("NafiTvApp", "Intercepted crash on thread: ${thread.name}", throwable)
            val msg = throwable.message ?: ""

            try {
                customImageLoader?.memoryCache?.clear()
                if (isOkHttpInitialized) {
                    sharedOkHttpClient.connectionPool.evictAll()
                }
            } catch (_: Throwable) {}

            if (thread != Looper.getMainLooper().thread) {
                Log.w("NafiTvApp", "Suppressed background exception on ${thread.name}: ${throwable.javaClass.simpleName} - $msg")
            } else {
                Log.e("NafiTvApp", "Intercepted fatal UI thread exception: ${throwable.javaClass.simpleName} - $msg", throwable)
                defaultHandler?.uncaughtException(thread, throwable)
            }
        }

        // Light background initialization & maintenance off the Main thread so startup is instant & crash-free
        kotlinx.coroutines.CoroutineScope(Dispatchers.IO).launch {
            try {
                com.example.util.SportsInteractionManager.init(this@NafiTvApp)
            } catch (_: Throwable) {}
            try {
                kotlinx.coroutines.delay(4000)
                com.example.util.StorageCleaner.cleanOnStartup(this@NafiTvApp)
            } catch (_: Throwable) {}
        }
    }

    fun isLowRamEnvironment(): Boolean {
        return try {
            val actManager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
            val memInfo = ActivityManager.MemoryInfo()
            actManager?.getMemoryInfo(memInfo)
            val totalRamMb = memInfo.totalMem / (1024 * 1024)
            val maxHeapMb = Runtime.getRuntime().maxMemory() / (1024 * 1024)
            actManager?.isLowRamDevice == true || totalRamMb <= 1500 || maxHeapMb <= 192
        } catch (_: Throwable) {
            false
        }
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
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .allowHardware(false) // Prevents IllegalArgumentException: Software rendering doesn't support hardware bitmaps
            .allowRgb565(false)
            .crossfade(true)
            .precision(Precision.INEXACT)
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

        @Volatile
        private var isOkHttpInitialized = false

        val sharedOkHttpClient: OkHttpClient by lazy {
            isOkHttpInitialized = true
            val isLowRam = try {
                instance.isLowRamEnvironment()
            } catch (_: Throwable) {
                false
            }
            val maxIdle = if (isLowRam) 4 else 8
            val keepAliveDuration = if (isLowRam) 30L else 120L

            val builder = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(maxIdle, keepAliveDuration, TimeUnit.SECONDS))
                .retryOnConnectionFailure(true)

            try {
                val cacheDirectory = File(instance.cacheDir, "nafitv_http_cache")
                val cacheSize = if (isLowRam) 5L * 1024 * 1024 else 12L * 1024 * 1024
                builder.cache(okhttp3.Cache(cacheDirectory, cacheSize))
            } catch (_: Throwable) {}

            builder.build()
        }
    }
}

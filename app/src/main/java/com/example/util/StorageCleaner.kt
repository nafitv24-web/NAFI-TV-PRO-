package com.example.util

import android.content.Context
import android.os.Environment
import android.util.Log
import coil.Coil
import com.example.NafiTvApp
import java.io.File

object StorageCleaner {
    private const val TAG = "StorageCleaner"

    /**
     * Calculates the total size in bytes of caches and volatile temporary storage.
     */
    fun calculateTotalStorageBytes(context: Context): Long {
        var total = 0L

        // 1. cacheDir (image cache, HTTP cache, media_cache, etc.)
        total += getFolderSize(context.cacheDir)

        // 2. externalCacheDir
        context.externalCacheDir?.let {
            total += getFolderSize(it)
        }

        // 3. Volatile media cache and temp files in filesDir
        val filesDir = context.filesDir
        if (filesDir.exists()) {
            filesDir.listFiles()?.forEach { file ->
                val name = file.name
                if (name.endsWith(".tmp") ||
                    name.startsWith("cache_") ||
                    name.startsWith("temp_")
                ) {
                    total += file.length()
                }
            }
        }

        // 4. Downloaded APKs in Downloads dir
        val downloadsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        if (downloadsDir != null && downloadsDir.exists()) {
            downloadsDir.listFiles()?.forEach { file ->
                if (file.name.endsWith(".apk") || file.name.endsWith(".tmp")) {
                    total += file.length()
                }
            }
        }

        // 5. Orphaned .part downloads
        val moviesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) ?: File(context.filesDir, "movies")
        if (moviesDir.exists()) {
            moviesDir.listFiles()?.forEach { file ->
                if (file.name.endsWith(".part") || file.name.endsWith(".tmp")) {
                    total += file.length()
                }
            }
        }

        return total
    }

    /**
     * Formats bytes to human readable string (e.g., "12.4 MB")
     */
    fun formatBytes(bytes: Long): String {
        if (bytes <= 0) return "0.0 MB"
        val mb = bytes / (1024.0 * 1024.0)
        return if (mb >= 1024) {
            String.format(java.util.Locale.US, "%.2f GB", mb / 1024.0)
        } else {
            String.format(java.util.Locale.US, "%.1f MB", mb)
        }
    }

    /**
     * Cleans up all unnecessary cache, temp files, legacy files, and old APKs.
     */
    fun cleanAppCacheAndTempFiles(context: Context): Long {
        val initialBytes = calculateTotalStorageBytes(context)

        try {
            // 1. Clear Coil Image Cache
            try {
                Coil.imageLoader(context).apply {
                    memoryCache?.clear()
                    diskCache?.clear()
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error clearing Coil cache", e)
            }

            // 2. Clear OkHttp Network Cache
            try {
                NafiTvApp.sharedOkHttpClient.cache?.evictAll()
            } catch (e: Exception) {
                Log.w(TAG, "Error evicting OkHttp cache", e)
            }

            // 3. Clean cacheDir
            deleteDirectoryContents(context.cacheDir)
            context.externalCacheDir?.let { deleteDirectoryContents(it) }

            // 4. Clean legacy cache_ and .tmp files in filesDir
            val filesDir = context.filesDir
            if (filesDir.exists()) {
                filesDir.listFiles()?.forEach { file ->
                    val name = file.name
                    if (name.endsWith(".tmp") ||
                        name.startsWith("cache_playlist_") ||
                        name.startsWith("cache_pl_mov_") ||
                        name.startsWith("cache_movies_pl_") ||
                        name.startsWith("cache_xtream_acc_") ||
                        name.equals("cache_livetv_v2.json") ||
                        name.equals("cache_sports_v2.json") ||
                        name.equals("cache_sports_v3.json") ||
                        name.equals("cache_movies_v2.json")
                    ) {
                        try { file.delete() } catch (_: Exception) {}
                    }
                }
            }

            // 5. Delete leftover APKs in Downloads dir
            val downloadsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            if (downloadsDir != null && downloadsDir.exists()) {
                downloadsDir.listFiles()?.forEach { file ->
                    if (file.name.endsWith(".apk") || file.name.endsWith(".tmp")) {
                        try { file.delete() } catch (_: Exception) {}
                    }
                }
            }

            // 6. Delete orphaned .part download files
            val moviesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) ?: File(context.filesDir, "movies")
            if (moviesDir.exists()) {
                moviesDir.listFiles()?.forEach { file ->
                    if (file.name.endsWith(".part") || file.name.endsWith(".tmp")) {
                        try { file.delete() } catch (_: Exception) {}
                    }
                }
            }

            // 7. Clear MediaRepository in-memory cache
            try {
                com.example.data.MediaRepository.clearMemoryCaches()
            } catch (_: Exception) {}

            System.runFinalization()
            System.gc()
        } catch (e: Exception) {
            Log.e(TAG, "Error during cache cleaning", e)
        }

        val afterBytes = calculateTotalStorageBytes(context)
        return (initialBytes - afterBytes).coerceAtLeast(0L)
    }

    /**
     * Silent cleanup called at startup on Dispatchers.IO
     */
    fun cleanOnStartup(context: Context) {
        try {
            // Remove orphaned .tmp files in filesDir
            context.filesDir.listFiles()?.forEach { file ->
                if (file.name.endsWith(".tmp")) {
                    try { file.delete() } catch (_: Exception) {}
                }
            }

            // Remove legacy cache files that are no longer used
            val legacy = listOf(
                "cache_livetv_v2.json",
                "cache_sports_v2.json",
                "cache_sports_v3.json",
                "cache_movies_v2.json"
            )
            for (fName in legacy) {
                try { File(context.filesDir, fName).delete() } catch (_: Exception) {}
            }

            // Remove old downloaded APKs
            val downloadsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            downloadsDir?.listFiles()?.forEach { file ->
                if (file.name.endsWith(".apk")) {
                    val age = System.currentTimeMillis() - file.lastModified()
                    if (age > 43200000L) { // older than 12 hours
                        try { file.delete() } catch (_: Exception) {}
                    }
                }
            }
            // Sanitize channel_status_prefs if it has bloated from previous sessions
            try {
                val sp = context.getSharedPreferences("channel_status_prefs", Context.MODE_PRIVATE)
                val failed = sp.getStringSet("failed_channels", emptySet())
                val verified = sp.getStringSet("verified_active", emptySet())
                val servers = sp.getStringSet("failed_servers", emptySet())
                if ((failed?.size ?: 0) > 100 || (verified?.size ?: 0) > 100 || (servers?.size ?: 0) > 60) {
                    sp.edit()
                        .putStringSet("failed_channels", failed?.toList()?.takeLast(80)?.toSet() ?: emptySet())
                        .putStringSet("verified_active", verified?.toList()?.takeLast(80)?.toSet() ?: emptySet())
                        .putStringSet("failed_servers", servers?.toList()?.takeLast(40)?.toSet() ?: emptySet())
                        .apply()
                }
            } catch (_: Exception) {}
        } catch (e: Exception) {
            Log.w(TAG, "Startup clean warning", e)
        }
    }

    private fun getFolderSize(file: File?): Long {
        if (file == null || !file.exists()) return 0L
        var size = 0L
        if (file.isDirectory) {
            file.listFiles()?.forEach { child ->
                size += getFolderSize(child)
            }
        } else {
            size += file.length()
        }
        return size
    }

    private fun deleteDirectoryContents(dir: File?) {
        if (dir == null || !dir.exists() || !dir.isDirectory) return
        dir.listFiles()?.forEach { child ->
            if (child.isDirectory) {
                deleteDirectoryContents(child)
                try { child.delete() } catch (_: Exception) {}
            } else {
                try { child.delete() } catch (_: Exception) {}
            }
        }
    }
}

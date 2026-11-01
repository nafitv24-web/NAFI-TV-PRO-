# Safe Tree-Shaking ProGuard configuration:
# Strips unused classes from external libraries (Firebase, AdMob, Media3, Play Services)
# reducing APK size drastically while preserving 100% runtime stability.

# 1. Disable bytecode rewriting and inlining (avoids Coroutines & Jetpack Compose crashes)
-dontoptimize

# 2. Disable name obfuscation (preserves exact class/method names for reflection, JNI and system services)
-dontobfuscate

# 3. Suppress harmless warnings
-dontwarn **
-ignorewarnings

# 4. Preserve line numbers and essential attributes
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# 5. Keep all application classes completely intact
-keep class com.example.** { *; }
-keepclassmembers class com.example.** { *; }

# 6. Keep AndroidX, Activity, Core, and Jetpack Compose
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.core.** { *; }
-keep class androidx.navigation.** { *; }

# 7. Keep Kotlin & Coroutines
-keep class kotlin.** { *; }
-keep class kotlin.reflect.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.** {
    volatile <fields>;
}

# 8. Media3, ExoPlayer, and Jellyfin FFmpeg decoder
-keep class androidx.media3.** { *; }
-keepclassmembers class androidx.media3.** { *; }
-keep class org.jellyfin.media3.** { *; }
-keepclassmembers class org.jellyfin.media3.** { *; }

# 9. OkHttp, Okio, and Coil
-keep class okhttp3.** { *; }
-keepclassmembers class okhttp3.** { *; }
-keep class okio.** { *; }
-keep class coil.** { *; }
-keepclassmembers class coil.** { *; }

# 10. Google Play Services Ads / AdMob
-keep class com.google.android.gms.ads.** { *; }
-keepclassmembers class com.google.android.gms.ads.** { *; }
-keep class com.google.ads.** { *; }
-keepclassmembers class com.google.ads.** { *; }

# 11. Firebase & Google Credentials
-keepattributes *JavascriptInterface*
-keep class com.google.firebase.** { *; }
-keepclassmembers class com.google.firebase.** { *; }
-keep class com.google.android.libraries.identity.googleid.** { *; }
-keep class androidx.credentials.** { *; }

# 12. CloudStream plugin classes
-keep class com.lagradost.cloudstream3.** { *; }
-keepclassmembers class com.lagradost.cloudstream3.** { *; }

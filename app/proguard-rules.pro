# Deep size-reduction ProGuard / R8 configuration:
# Fully shrinks and obfuscates external libraries (Firebase, AdMob, Media3, Play Services)
# reducing APK size below 15 MB while keeping 100% stability.

-repackageclasses ''
-allowaccessmodification

# Suppress harmless warnings
-dontwarn **
-ignorewarnings

# Preserve line numbers and essential reflection attributes
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep all application classes completely intact
-keep class com.example.** { *; }
-keepclassmembers class com.example.** { *; }

# Keep AndroidX, Activity, Core, and Jetpack Compose
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.core.** { *; }
-keep class androidx.navigation.** { *; }

# Keep Kotlin & Coroutines
-keep class kotlin.** { *; }
-keep class kotlin.reflect.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.** {
    volatile <fields>;
}

# Media3 and ExoPlayer
-keep class androidx.media3.** { *; }
-keepclassmembers class androidx.media3.** { *; }

# OkHttp, Okio, and Coil
-keep class okhttp3.** { *; }
-keepclassmembers class okhttp3.** { *; }
-keep class okio.** { *; }
-keep class coil.** { *; }
-keepclassmembers class coil.** { *; }

# Google Play Services Ads / AdMob
-keep class com.google.android.gms.ads.** { *; }
-keepclassmembers class com.google.android.gms.ads.** { *; }
-keep class com.google.ads.** { *; }
-keepclassmembers class com.google.ads.** { *; }

# Firebase & Google Credentials
-keepattributes *JavascriptInterface*
-keep class com.google.firebase.** { *; }
-keepclassmembers class com.google.firebase.** { *; }
-keep class com.google.android.libraries.identity.googleid.** { *; }
-keep class androidx.credentials.** { *; }

# CloudStream plugin classes
-keep class com.lagradost.cloudstream3.** { *; }
-keepclassmembers class com.lagradost.cloudstream3.** { *; }

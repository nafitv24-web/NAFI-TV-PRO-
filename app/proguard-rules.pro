# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Preserve line numbers and essential reflection attributes
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep all application classes, composables, viewmodels, and managers completely intact
-keep class com.example.** { *; }
-keepclassmembers class com.example.** { *; }

# Keep CloudStream plugin classes
-keep class com.lagradost.cloudstream3.** { *; }
-keepclassmembers class com.lagradost.cloudstream3.** { *; }

# Keep Google Mobile Ads / AdMob classes
-keep class com.google.android.gms.ads.** { *; }
-keepclassmembers class com.google.android.gms.ads.** { *; }
-keep class com.google.ads.** { *; }
-keepclassmembers class com.google.ads.** { *; }

# Jetpack Compose and Lifecycle
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class androidx.lifecycle.** { *; }

# Media3 & ExoPlayer
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**

# Jellyfin FFmpeg decoder
-keep class org.jellyfin.media3.** { *; }
-dontwarn org.jellyfin.media3.**

# OkHttp & Okio
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }

# Firebase & Google Play Services
-keepattributes *JavascriptInterface*
-keepclassmembers class * {
    @com.google.firebase.firestore.** *;
}
-dontwarn com.google.android.gms.**
-dontwarn com.google.firebase.**

# Coil Image Loading
-keep class coil.** { *; }
-dontwarn coil.**

# Suppress harmless warnings during R8 optimization
-dontwarn **
-ignorewarnings

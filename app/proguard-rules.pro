# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Preserve line numbers and essential reflection attributes
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep app data models
-keep class com.example.model.** { *; }
-keep class com.example.data.** { *; }
-keep class com.lagradost.cloudstream3.** { *; }

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

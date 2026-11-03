# ProGuard / R8 configuration for NAFI TV Pro
# Safe, high-performance size reduction without breaking entry points or reflection

# Suppress harmless library warnings
-dontwarn **
-ignorewarnings

# Preserve line numbers and essential reflection attributes
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep Android Components (Activity, Application, Service, Receiver, Provider)
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep application entry points and core classes
-keep public class com.example.NafiTvApp { *; }
-keep public class com.example.MainActivity { *; }
-keep class com.example.** { *; }
-keepclassmembers class com.example.** { *; }

# Keep data classes and JSON models
-keepclassmembers class com.example.model.** {
    <fields>;
    <init>(...);
}

# Keep AndroidX & Jetpack Compose runtime
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.navigation.** { *; }

# Keep Coroutines
-keep class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }

# Keep Media3 / ExoPlayer
-keep class androidx.media3.** { *; }
-keepclassmembers class androidx.media3.** { *; }

# Keep OkHttp & Coil
-keep class okhttp3.** { *; }
-keep class coil.** { *; }

# Keep Google Play Services Ads / AdMob Lite
-keep class com.google.android.gms.ads.** { *; }
-keepclassmembers class com.google.android.gms.ads.** { *; }

# Keep Firebase & Google Auth
-keepattributes *JavascriptInterface*
-keep class com.google.firebase.** { *; }
-keepclassmembers class com.google.firebase.** { *; }
-keep class androidx.credentials.** { *; }
-keep class com.google.android.libraries.identity.googleid.** { *; }

# Keep CloudStream plugin interfaces
-keep class com.lagradost.cloudstream3.** { *; }

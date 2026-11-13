# ProGuard / R8 configuration for NAFI TV Pro
# Safe size optimization with full runtime stability

# Suppress harmless third-party library warnings
-dontwarn **
-ignorewarnings

# Preserve line numbers for stack traces and essential annotations
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,RuntimeVisibleAnnotations,AnnotationDefault

# Keep Android Application Components
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep application entry points & model classes
-keep public class com.example.NafiTvApp { *; }
-keep public class com.example.MainActivity { *; }
-keep class com.example.model.** { *; }
-keepclassmembers class com.example.model.** { *; }
-keepclassmembers enum com.example.ui.** { *; }

# Keep WebView JavaScript interfaces
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep CloudStream interfaces
-keep class com.lagradost.cloudstream3.** { *; }

# Keep Coil ImageLoader, Fetchers, Decoders, and Compose integration
-keep class coil.** { *; }
-keepclassmembers class coil.** { *; }
-keep class * implements coil.ImageLoaderFactory { *; }
-keep class * implements coil.decode.Decoder$Factory { *; }
-keep class * implements coil.fetch.Fetcher$Factory { *; }

# Keep Kotlin Coroutines & ServiceLoader dispatchers
-keep class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.android.AndroidDispatcherFactory { <init>(); }
-keepclassmembers class kotlinx.coroutines.android.AndroidExceptionPreHandler { <init>(); }

# Keep Jetpack Compose, Lifecycle, Activity, Navigation & Startup runtime
-keep class androidx.compose.runtime.** { *; }
-keepclassmembers class androidx.compose.ui.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.navigation.** { *; }
-keep class androidx.startup.** { *; }

# Keep Media3 / ExoPlayer reflective constructors & renderers
-keep class androidx.media3.** { *; }
-keepclassmembers class androidx.media3.** { *; }
-keep interface androidx.media3.** { *; }

# Keep OkHttp & Okio
-keep class okhttp3.** { *; }
-keep class okio.** { *; }

# Keep Google Play Services Ads (AdMob) & Dynamite classes
-keep class com.google.android.gms.ads.** { *; }
-keepclassmembers class com.google.android.gms.ads.** { *; }
-keep class com.google.android.gms.dynamic.** { *; }
-keep class com.google.android.gms.common.** { *; }

# Keep Firebase Auth, Credentials & Google Sign-In
-keep class com.google.firebase.** { *; }
-keepclassmembers class com.google.firebase.** { *; }
-keep class androidx.credentials.** { *; }
-keep class com.google.android.libraries.identity.googleid.** { *; }


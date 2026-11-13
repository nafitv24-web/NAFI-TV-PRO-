# ProGuard / R8 configuration for NAFI TV Pro
# Aggressive size optimization with safe reflection preservation

# Suppress harmless third-party library warnings
-dontwarn **
-ignorewarnings

# Preserve line numbers for stack traces and essential annotations
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep Android Application Components
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep application entry points
-keep public class com.example.NafiTvApp { *; }
-keep public class com.example.MainActivity { *; }

# Keep data models used in JSON serialization/deserialization
-keep class com.example.model.** { *; }
-keepclassmembers class com.example.model.** { *; }

# Keep WebView JavaScript interfaces
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep CloudStream interfaces if used
-keep class com.lagradost.cloudstream3.** { *; }


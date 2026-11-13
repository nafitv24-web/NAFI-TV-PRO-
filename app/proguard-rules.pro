# ProGuard / R8 configuration for NAFI TV Pro
# Lean code shrinking & optimization while preserving reflection/JS/plugin entry points

-dontwarn **
-ignorewarnings

# Preserve line numbers for readable stack traces & Kotlin annotations
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations,AnnotationDefault
-keep class kotlin.Metadata { *; }

# Keep Android Application Components & CoreComponentFactory
-keep class androidx.core.app.CoreComponentFactory { *; }
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep application entry points, enums & model classes
-keep public class com.example.NafiTvApp { *; }
-keep public class com.example.MainActivity { *; }
-keep class com.example.model.** { *; }
-keepclassmembers class com.example.model.** { *; }
-keepclassmembers enum com.example.** { *; }

# Keep WebView JavaScript interfaces
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep CloudStream plugin interfaces loaded dynamically via DexClassLoader
-keep class com.lagradost.cloudstream3.** { *; }
-keepclassmembers class com.lagradost.cloudstream3.** { *; }


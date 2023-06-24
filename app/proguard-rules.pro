# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
# -renamesourcefileattribute SourceFile
# -printusage usage.txt
# -printseeds seeds.txt

#-keep public interface org.deiverbum.app.data.db.dao.TodayDao
-keep class org.deiverbum.app.core.** { *; }
-keep class org.deiverbum.app.data.** { *; }
-keep class org.deiverbum.app.di.** { *; }
-keep class org.deiverbum.app.domain.** { *; }

-keep class org.deiverbum.app.model.** { *; }
-keep class org.deiverbum.app.presentation.** { *; }
#-keep class org.deiverbum.app.ui.** { *; }
-keep class org.deiverbum.app.util.** { *; }
#-keep class org.deiverbum.app.viewmodel.** { *; }
-keep class org.deiverbum.app.workers.** { *; }
-keep class org.deiverbum.app.BaseApplication

-keepdirectories assets/*


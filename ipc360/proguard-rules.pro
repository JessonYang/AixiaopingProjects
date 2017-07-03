# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\studio\studio\androidStudioBundle\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
#-dump build/class_files.txt #apk 包内所有 class 的内部结构
#-printseeds build/seeds.txt  #未混淆的类和成员
#-printusage build/unused.txt #列出从 apk 中删除的代码
-printmapping build/mapping.txt #混淆前后的映射

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用算法

-keepattributes Exceptions,InnerClasses,Signature,*Annotation*   #保持函数的异常，签名，注解，还有内部类不被去掉
#-keepattributes SourceFile,LineNumberTable #保持源文件和行号的信息,用于混淆后定位错误位置  
#-keepattributes EnclosingMethod,Deprecated

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-keep class * extends java.lang.annotation.Annotation {*;}
-keep class **.R$* {*;}
-keep class **.R{*;}


-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
   private  native <methods>;
}
-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    static native <methods>;
}
-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
   private  static native <methods>;
}

-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}
-assumenosideeffects class android.util.Log{# 禁止log输出
    public static *** d(...);
    public static *** i(...);
    public static *** v(...);
    public static *** e(...);
    public static *** w(...);
}
#com.xmcamera  请在使用sdk jar包的时候开启使用以下语句
-dontwarn com.xmcamera.**
-keep class com.xmcamera.**{*;}
-keep class voice.**{*;}
#slf4j log4j 假如使用extra功能请加入以下语句
-dontwarn de.mindpipe.android.logging.log4j.**
-dontwarn org.apache.log4j.**
-dontwarn org.slf4j.**
-keep public class de.mindpipe.android.logging.log4j.**{*;}
-keep public class org.apache.log4j.**{*;}
-keep public class org.slf4j.**{*;}
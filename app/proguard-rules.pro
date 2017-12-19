# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Program Files\Android\sdk/tools/proguard/proguard-android.txt
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

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-ignorewarnings
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.view.View
-keep class * extends java.lang.annotation.Annotation{ *; }

#greendao
-keep class de.greenrobot.dao.** {*;}
-keepclassmembers class * extends com.weslide.lovesmallscreen.core.BaseDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties {
    public *;
}
-keep class com.weslide.lovesmallscreen.models.AcquireScore{*;}
-keep class com.weslide.lovesmallscreen.models.AdvertImg{*;}
-keep class com.weslide.lovesmallscreen.models.Location{*;}
-keep class com.weslide.lovesmallscreen.models.Zone{*;}
-keep class com.weslide.lovesmallscreen.core.SaveMoneyWebActivity$InJavaScriptGetBody{*;}
-keep class com.weslide.lovesmallscreen.core.WebActivity$InJavaScriptGetBody{*;}
-keep class com.weslide.lovesmallscreen.activitys.TaoKeActivity$InJavaScriptGetBody{*;}
-keep class com.weslide.lovesmallscreen.activitys.ChangeGoodsWebActivity$InJavaScriptGetBody{*;}
#API
-keep class com.weslide.lovesmallscreen.network.API{*;}

-keepclasseswithmembers class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-dontwarn android.support.**



#library/libs/BaiduLBS_Android.jar
#library/libs/bugly_crash_release__2.1.1.jar
#library/libs/SocialSDK_alipay.jar
#library/libs/SocialSDK_QQZone_3.jar
#library/libs/SocialSDK_WeiXin_1.jar
#library/libs/SocialSDK_WeiXin_2.jar
#library/libs/umeng_social_sdk.jar

#--------------------------------------------友盟----------------------------------------------------#
 -dontshrink
 -dontoptimize
 -dontwarn com.google.android.maps.**
 -dontwarn android.webkit.WebView
 -dontwarn com.umeng.**
 -dontwarn com.tencent.weibo.sdk.**
 -dontwarn com.facebook.**
 -keep public class javax.**
 -keep public class android.webkit.**
 -dontwarn android.support.v4.**
 -keep enum com.facebook.**
 -keepattributes Exceptions,InnerClasses,Signature
 -keepattributes *Annotation*
 -keepattributes SourceFile,LineNumberTable

 -keep public interface com.facebook.**
 -keep public interface com.tencent.**
 -keep public interface com.umeng.socialize.**
 -keep public interface com.umeng.socialize.sensor.**
 -keep public interface com.umeng.scrshot.**

 -keep public class com.umeng.socialize.* {*;}


 -keep class com.facebook.**
 -keep class com.facebook.** { *; }
 -keep class com.umeng.scrshot.**
 -keep public class com.tencent.** {*;}
 -keep class com.umeng.socialize.sensor.**
 -keep class com.umeng.socialize.handler.**
 -keep class com.umeng.socialize.handler.*
 -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
 -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

 -keep class im.yixin.sdk.api.YXMessage {*;}
 -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

 -dontwarn twitter4j.**
 -keep class twitter4j.** { *; }

 -keep class com.tencent.** {*;}
 -dontwarn com.tencent.**
 -keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
 -keep public class com.umeng.soexample.R$*{
     public static final int *;
 }
 -keep class com.tencent.open.TDialog$*
 -keep class com.tencent.open.TDialog$* {*;}
 -keep class com.tencent.open.PKDialog
 -keep class com.tencent.open.PKDialog {*;}
 -keep class com.tencent.open.PKDialog$*
 -keep class com.tencent.open.PKDialog$* {*;}

 -keep class com.sina.** {*;}
 -dontwarn com.sina.**
 -keep class  com.alipay.share.sdk.** {
    *;
 }
 -keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
 }

 -keep class com.linkedin.** { *; }
 -keepattributes Signature
#--------------------------------------------友盟----------------------------------------------------#

#支付宝
 -keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

-dontwarn org.greenrobot.eventbus.**
-keep class org.greenrobot.eventbus.** {*;}
-keepclassmembers class ** {
    public void onEvent*(**);
}

#个推
-dontwarn com.igexin.**
-keep class com.igexin.**{*;}

#百度地图
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

#腾讯Bugly
-keep public class com.tencent.bugly.**{*;}

#Butterknife依赖注入
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#GSON （必要）暂不清楚其作用
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature


# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }


# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }


##---------------End: proguard configuration for Gson  ----------

#保留行号等信息，方便日志收集及调试
-keepattributes SourceFile,LineNumberTable
#-renamesourcefileattribute SourceFile

#混淆时，删除日志输出
-assumenosideeffects class android.util.Log{
	public static *** d(...);
	public static *** i(...);
	public static *** v(...);
	public static *** e(...);
}
-assumenosideeffects class com.weslide.weslideapp.log.Log{
	public static *** d(...);
	public static *** i(...);
	public static *** v(...);
	public static *** e(...);
}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#OKHTTP Glide 整合
-keep public class * implements com.bumptech.glide.module.GlideModule

-dontwarn rx.**
-keep class rx.** { *; }


#IPC360
-dontwarn com.xmcamera.**
-keep class com.xmcamera.**{*;}
-keep class voice.**{*;}
#如果同时使用了extra jar包，还需加上
-dontwarn de.mindpipe.android.logging.log4j.**
-dontwarn org.apache.log4j.**
-dontwarn org.slf4j.**
-keep public class de.mindpipe.android.logging.log4j.**{*;}
-keep public class org.apache.log4j.**{*;}
-keep public class org.slf4j.**{*;}


#视频播放
-dontwarn tv.danmaku.**
-keep class tv.danmaku.**{*;}

-keepattributes Signature
-keep class com.taobao.* {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
-keep class org.json.** {*;}
-keep class com.ali.auth.** {*;}


-keep class com.payeco.android.plugin.view.MyPasswordView
-keep class com.payeco.android.plugin.view.ShimmerTextView
-keep interface com.payeco.android.plugin.PayecoPluginPayCallBack{*;}
-keepclasseswithmembernames class com.payeco.android.plugin.PayecoPluginPayIn{
	public static void doPay(android.app.Activity,java.util.Map,com.payeco.android.plugin.PayecoPluginPayCallBack);
	public static java.lang.String getVersionName();
}

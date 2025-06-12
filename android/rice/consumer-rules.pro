-keepattributes !SourceFile
-dontshrink

-keep class f2.c4
-keep class j5.r2 #广播
-keep class b5.w8{native<methods>;}
-keep class k2.B0{static<methods>;}
-keep class h4.H1{static<methods>;}
-keep class b5.aa.c.b1{static<methods>;}
-keep class y1.u9
-keep class m3.H1 #H5
-keep class m3.M0 #H5
-keep class b5.c.m.A1
-keep class h4.B1
-keep class f2.f1{static<methods>;}

#appsflyer start
# keep init adpost
-keep class com.appsflyer.** { *; }
-keep class kotlin.jvm.internal.** { *; }
-keep public class com.android.installreferrer.** { *; }
#appsflyer end

#tradplus start
-keep public class com.tradplus.** { *; }
-keep class com.tradplus.ads.** { *; }
#tradplus end
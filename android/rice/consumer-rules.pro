-keep class com.wild.rice.RiceReceiver
-keep class com.wild.rice.WildNative{
    native <methods>;
}


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
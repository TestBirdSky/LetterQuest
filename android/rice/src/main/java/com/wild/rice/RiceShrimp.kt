package com.wild.rice

import android.app.Application
import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.rice.jar.RiceActivityCallback
import com.rice.jar.RiceBoolCache
import com.rice.jar.RiceJellyCache
import com.rice.jar.RiceMill
import com.rice.jar.RiceWarehouseCache
import com.tencent.mmkv.MMKV

/**
 * Date：2025/3/19
 * Describe:
 */
class RiceShrimp : AppsFlyerConversionListener {

    companion object {
        val mApplication: Application by lazy { Tools.mApp }
        val mmkv: MMKV by lazy { MMKV.defaultMMKV() }
    }

    fun riceShrimpStart(context: Context) {
        RiceJellyCache.riceInit(context)
        val riceMill = RiceMill(context)
        Tools.eventImpl = riceMill
        riceMill.riceFetch()
        RiceActivityCallback(riceMill).registerThis(context as Application)
    }

    private var riceKey by RiceWarehouseCache("rice")
    fun registerAf(context: Context) {
        RiceCenter.afRegister(context, this)
        if (riceKey == "rice") {
            runCatching {
                Firebase.messaging.subscribeToTopic("mill_fcm").addOnSuccessListener {
                    riceKey = "mill"
                }
            }
        }
    }

    private var isPostAf by RiceBoolCache(false, "af_status_post")

    override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
        if (isPostAf.not()) {
            if (p0 != null && p0["af_status"] != "Organic") {
                isPostAf = true
                Tools.eventImpl.postEventOpen("non_organic", "")
            }
        }
    }

    override fun onConversionDataFail(p0: String?) = Unit
    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) = Unit
    override fun onAttributionFailure(p0: String?) = Unit
}
package com.wild.rice

import android.app.Application
import android.content.Context
import android.os.Build
import android.webkit.WebView
import androidx.annotation.Keep
import com.appsflyer.AppsFlyerConversionListener
import com.rice.jar.BaseNetImpl
import com.rice.jar.JarActivityCallback
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
        JarActivityCallback(riceMill).registerThis(context as Application)
    }

    fun registerAf(context: Context) {
        RiceCenter.afRegister(context, this)
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
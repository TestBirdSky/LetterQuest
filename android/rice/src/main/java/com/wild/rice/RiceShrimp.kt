package com.wild.rice

import android.app.Application
import android.os.Build
import android.webkit.WebView
import androidx.annotation.Keep
import com.appsflyer.AppsFlyerConversionListener
import com.rice.jar.BaseNetImpl
import com.rice.jar.JarActivityCallback
import com.rice.jar.RiceJellyCache
import com.rice.jar.RiceMill
import com.rice.jar.RiceWarehouseCache
import com.tencent.mmkv.MMKV

/**
 * Date：2025/3/19
 * Describe:
 */
class RiceShrimp : AppsFlyerConversionListener {
    private lateinit var mBaseNetImpl: BaseNetImpl

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName: String = Application.getProcessName()
            if (!mApplication.packageName.equals(processName)) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
    }

    companion object {
        lateinit var mApplication: Application
        lateinit var mmkv: MMKV

        @JvmStatic
        @Keep
        fun riceStart(application: Application) {
            MMKV.initialize(application)
            mApplication = application
            mmkv = MMKV.defaultMMKV()
            val riceShrimp = RiceShrimp()
            riceShrimp.riceShrimpStart()
        }
    }

    fun riceShrimpStart() {
        if (RiceCenter.isRicePro(mApplication)) {
            RiceJellyCache.riceInit(mApplication)
            val riceMill = RiceMill(mApplication)
            mBaseNetImpl = riceMill
            RiceCenter.afRegister(mApplication, this)
            riceMill.riceFetch()
            JarActivityCallback(riceMill).registerThis(mApplication)
        }
    }
    private var isPostAf by RiceWarehouseCache()

    override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
        if (p0 != null && p0["af_status"] != "Organic") {
            if (isPostAf.isBlank()) {
                isPostAf = "post"
                mBaseNetImpl.postEvent("non_organic", "")
            }
        }
    }

    override fun onConversionDataFail(p0: String?) = Unit
    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) = Unit
    override fun onAttributionFailure(p0: String?) = Unit
}
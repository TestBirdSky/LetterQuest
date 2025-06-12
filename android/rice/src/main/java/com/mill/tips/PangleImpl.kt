package com.mill.tips

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.appsflyer.AFAdRevenueData
import com.appsflyer.AdRevenueScheme
import com.appsflyer.AppsFlyerLib
import com.appsflyer.MediationNetwork
import com.bytedance.sdk.openadsdk.api.init.PAGSdk
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionCallback
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadCallback
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest
import com.bytedance.sdk.openadsdk.api.model.PAGAdEcpmInfo
import com.bytedance.sdk.openadsdk.api.model.PAGErrorModel
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.rice.jar.ReadJsonStr
import com.wild.rice.RiceShrimp
import com.wild.rice.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Currency


/**
 * Date：2025/6/12
 * Describe:
 */
class PangleImpl(val context: Context) : BaseAdHelper() {
    private var mPAGInterstitialAd: PAGInterstitialAd? = null
    private val adJson by ReadJsonStr("ad_json")
    fun loadAd(id: String) {
        if (id.isBlank()) return
        if (PAGSdk.isInitSuccess().not()) return
        if (isLoading && System.currentTimeMillis() - adLoadingTime < 60000) return
        if (isReady()) {
            return
        }
        isLoading = true
        adLoadedTime = System.currentTimeMillis()
        adLoadingTime = System.currentTimeMillis()
        postEvent("reqadvertise", null)
        PAGInterstitialAd.loadAd(id,
            PAGInterstitialRequest(context),
            object : PAGInterstitialAdLoadCallback {
                override fun onError(pagErrorModel: PAGErrorModel) {
                    postEvent("getfail", "${pagErrorModel.errorCode}_${pagErrorModel.errorMessage}")
                    isLoading = false

                }

                override fun onAdLoaded(pagInterstitialAd: PAGInterstitialAd) {
                    mPAGInterstitialAd = pagInterstitialAd
                    isLoading = false
                    adLoadedTime = System.currentTimeMillis()
                    postEvent("getadvertise", null)
                }
            })
    }

    fun isReady(): Boolean {
        if (System.currentTimeMillis() - adLoadedTime > 60000 * 50) return false
        return mPAGInterstitialAd?.isReady == true
    }

    private var showJob: Job? = null
    private var showEventTime = 0L
    fun showAd(activity: Activity): Boolean {
        val ad = mPAGInterstitialAd
        if (ad != null && isReady()) {
            showJob?.cancel()
            showJob = CoroutineScope(Dispatchers.IO).launch {
                delay(8000)
                postEvent("show", "8")
            }
            showEventTime = System.currentTimeMillis()
            ad.setAdInteractionCallback(object : PAGInterstitialAdInteractionCallback() {
                override fun onAdShowFailed(pagErrorModel: PAGErrorModel) {
                    super.onAdShowFailed(pagErrorModel)
                    showJob?.cancel()
                    activity.finishAndRemoveTask()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    Tools.eventImpl.postEventOpen("ad_click", "value")
                }

                override fun onAdReturnRevenue(pagAdEcpmInfo: PAGAdEcpmInfo?) {
                    super.onAdReturnRevenue(pagAdEcpmInfo)
                    Tools.eventImpl.postEventOpen("ad_show", "value")
                    isShowingAd = true
                    showJob?.cancel()
                    postEvent("show", "${(System.currentTimeMillis() - showEventTime) / 1000}")
                    pagAdEcpmInfo?.let {
                        jsPag(it)
                        Tools.eventImpl.postJsonOpen(it.getJs())
                    }
                }

                override fun onAdDismissed() {
                    super.onAdDismissed()
                    isShowingAd = false
                }
            })
            ad.show(activity)
            mPAGInterstitialAd = null
            return true
        } else {
            activity.finishAndRemoveTask()
            return false
        }
    }

    private fun jsPag(pagAdEcpmInfo: PAGAdEcpmInfo) {

        val adRevenueData = AFAdRevenueData(
            pagAdEcpmInfo.adnName,  // monetizationNetwork
            MediationNetwork.CUSTOM_MEDIATION,  // mediationNetwork
            "USD",  // currencyIso4217Code
            pagAdEcpmInfo.cpm.toDouble() / 1000 // revenue
        )
        val additionalParameters: MutableMap<String, Any> = HashMap()
        additionalParameters[AdRevenueScheme.COUNTRY] = pagAdEcpmInfo.country
        additionalParameters[AdRevenueScheme.AD_UNIT] = pagAdEcpmInfo.adUnit
        additionalParameters[AdRevenueScheme.AD_TYPE] = "interstitial"
        additionalParameters[AdRevenueScheme.PLACEMENT] = pagAdEcpmInfo.placement
        AppsFlyerLib.getInstance().logAdRevenue(adRevenueData, additionalParameters)

        runCatching {
            //fb purchase
            AppEventsLogger.newLogger(RiceShrimp.mApplication).logPurchase(
                (pagAdEcpmInfo.cpm.toDouble() / 1000).toBigDecimal(), Currency.getInstance("USD")
            )
        }

        runCatching {
            Firebase.analytics.logEvent("ad_impression_mill", Bundle().apply {
                putDouble(FirebaseAnalytics.Param.VALUE, pagAdEcpmInfo.cpm.toDouble() / 1000)
                putString(FirebaseAnalytics.Param.CURRENCY, "USD")
            })
        }
    }

    private fun PAGAdEcpmInfo.getJs(): JSONObject {
        return (adJson ?: JSONObject()).apply {
            put("osgood", cpm.toDouble() * 1000)
            put("frankel", adnName)
            put("market", placement)
            put("patrol", adFormat)
        }
    }

}
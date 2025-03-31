package com.rice.jar

import android.app.Activity
import android.content.Context
import com.tradplus.ads.base.bean.TPAdError
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.open.interstitial.InterstitialAdListener
import com.tradplus.ads.open.interstitial.TPInterstitial
import com.wild.rice.RiceShrimp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject


/**
 * Date：2025/3/19
 * Describe:
 */
abstract class BaseAdCenter : BaseNetImpl(), ConfigureListener, InterstitialAdListener {
    private var adInterstitial: TPInterstitial? = null
    private var isLoading = false
    private var adLoadingTime = 0L
    private var adLoadedTime = 0L
    var isCanPostLog = true
    var isShowingAd = false
    private var adIdStr = ""
    private val nameKey = mapOf(
        "getadmin" to "getstring",
        "getfail" to "string1",
        "show" to "t",
        "showfailer" to "string3",
        "session_front" to "time",
        "starup" to "time",
        "first_start" to "time",
        "delaytime" to "time",
        "ispass" to "string",
    )
    private val listStrMust = arrayListOf("getadmin", "non_organic", "first_start")

    override fun refresh(js: JSONObject) {
        runCatching {
            adIdStr = js.optString("blank_rice_id")
        }
    }

    override fun postEvent(name: String, value: String?) {
        if (isCanPostLog.not() && listStrMust.contains(name).not()) {
            log("cancel post $name --$value")
            return
        }
        if (value != null) {
            postEventRice(name, Pair(nameKey[name] ?: "", value))
        } else {
            postEventRice(name)
        }
    }

    protected fun loadAdCenter(context: Context) {
        if (RiceJellyCache.isInitSuccess.not()) return
        if (adIdStr.isBlank()) return
        if (isLoading && System.currentTimeMillis() - adLoadingTime < 80000) return
        if (adIsReady()) return
        loadRice(context)
    }

    private fun loadRice(context: Context) {
        isLoading = true
        adLoadedTime = System.currentTimeMillis()
        adLoadingTime = System.currentTimeMillis()
        if (adInterstitial == null) {
            adInterstitial = TPInterstitial(context, adIdStr)
        }
        postEvent("reqadvertise", null)
        adInterstitial?.setAdListener(this)
        adInterstitial?.loadAd()
    }

    protected fun adIsReady(): Boolean {
        if (System.currentTimeMillis() - adLoadedTime > 60000 * 50) return false
        return adInterstitial?.isReady == true
    }

    private var showJob: Job? = null
    private var showEventTime = 0L
    private var eventClose: (() -> Unit)? = null

    fun showAd(activity: Activity): Boolean {
        if (adIsReady()) {
            showJob?.cancel()
            eventClose = {
                activity.finishAndRemoveTask()
            }
            showJob?.cancel()
            showJob = CoroutineScope(Dispatchers.IO).launch {
                delay(5000)
                postEvent("show", "5")
            }
            showEventTime = System.currentTimeMillis()
            adInterstitial?.setAdListener(this)
            adInterstitial?.showAd(activity, "")
            return true
        } else {
            postEvent("showfailer", "ad not ready")
            activity.finishAndRemoveTask()
            return false
        }
    }

    override fun onAdLoaded(tpAdInfo: TPAdInfo?) {
        isLoading = false
        adLoadedTime = System.currentTimeMillis()
        postEvent("getadvertise", null)
    }

    override fun onAdClicked(tpAdInfo: TPAdInfo?) {
        log("onAdClicked-->")
    }

    override fun onAdImpression(tpAdInfo: TPAdInfo?) {
        isShowingAd = true
        showJob?.cancel()
        postEvent("show", "${(System.currentTimeMillis() - showEventTime) / 1000}")
        loadAdCenter(RiceShrimp.mApplication)
        tpAdInfo?.let { tp ->
            postJson(getRiceCommon().apply {
                put("carbine", JSONObject().apply {
                    put("podge", tp.ecpm.toDouble() * 1000)
                    put("consul", "USD")
                    put("ahead", tp.adSourceName)
                    put("mckenna", "tradplus")
                    put("soy", tp.adSourcePlacementId)
                    put("liaison", "Interstitial")
                    put("erosive", tp.format)
                })
            })
        }
    }

    override fun onAdFailed(error: TPAdError?) {
        isLoading = false
        postEvent("getfail", "${error?.errorCode}_${error?.errorMsg}")
    }

    override fun onAdClosed(tpAdInfo: TPAdInfo?) {
        isShowingAd = false
        eventClose?.invoke()
        eventClose = null
    }

    override fun onAdVideoStart(tpAdInfo: TPAdInfo?) = Unit

    override fun onAdVideoEnd(tpAdInfo: TPAdInfo?) = Unit

    override fun onAdVideoError(tpAdInfo: TPAdInfo?, error: TPAdError?) {
        showJob?.cancel()
        isShowingAd = false
        postEvent("showfailer", "${error?.errorCode}_${error?.errorMsg}")
        eventClose?.invoke()
        eventClose = null
    }
}
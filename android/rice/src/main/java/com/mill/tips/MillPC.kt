package com.mill.tips

import android.app.Activity
import com.rice.jar.RiceIntCache
import com.rice.jar.RiceJellyCache
import com.rice.jar.RiceWarehouseCache
import com.wild.rice.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date：2025/6/12
 * Describe:
 */
abstract class MillPC {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private var isPlanIng = false
    private var timeCheck = 60000L
    private var timePeriod = 60000L
    private var timeInstall = 50000L

    var maxHourShowAd = 100
    var maxDayShowAd = 100
    var maxDayClickAd = 100
    private var riceMaxJump = 100

    private var retryNum by RiceIntCache(def = 0)

    var showHourNum by RiceWarehouseCache()
    var showDayNum by RiceWarehouseCache()
    var clickDayNum by RiceIntCache()

    var needAdNow = false
    var lastShowTime = 0L
    private var isLimit = false
    private var isFailed = false

    lateinit var mEventImpl: EventImpl

    fun setNumClear(activity: Activity) {
        retryNum = 0
        Class.forName("b5.aa.c.b1").getMethod("a1", Any::class.java).invoke(null, activity)
    }

    private fun postEvent(string: String, value: String?) {
        mEventImpl.postEventOpen(string, value)
    }


    fun eventMe() {
        if (retryNum > riceMaxJump) {
            postEvent("jumpfail", null)
            isFailed = true
            return
        }
        val s = Tools.mAdCenter.check(this)
        if (s == "rice_limit") {
            isLimit = true
            return
        }
        if (s.isBlank()) {
            Tools.mAdCenter.mPangleImpl.loadAd(Tools.adIdStr)
            if (System.currentTimeMillis() - RiceJellyCache.mRiceInstallTime < timeInstall) {
                postEvent("ispass", "rice_ii")
                return
            }
            if (System.currentTimeMillis() - lastShowTime < timePeriod) {
                postEvent("ispass", "rice_period")
                return
            }
            postEvent("ispass", "null")
            val isReady = Tools.mAdCenter.mPangleImpl.isReady()
            if (isReady || Tools.mAdCenter.mPangleImpl.isShowingAd.not()) {
                needAdNow = false
                if (isReady) {
                    postEvent("isready", null)
                }
                mEventImpl.postEventOpen("show_mill_event", "")
            } else if (Tools.mAdCenter.mPangleImpl.isShowingAd) {
                if (System.currentTimeMillis() - lastShowTime > 90000) {
                    mEventImpl.postEventOpen("clear_page", "")
                }
            }
        }
    }

    fun startTask() {
        if (isPlanIng) return
        isPlanIng = true
        mainScope.launch {
            delay(800)
            if (retryNum > riceMaxJump) {
                return@launch
            }
            mEventImpl.postEventOpen("load_ad_start", "")
            Class.forName("b5.w8").getMethod("b1", Int::class.java, Any::class.java)
                .invoke(null, 7, 900)
            delay(1200)
            while (isFailed.not()) {
                delay(timeCheck)
                eventMe()
            }
        }
    }

    fun fetchTime(string: String) {
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

}
package com.rice.jar

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mill.tips.EventImpl
import com.wild.rice.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import y1.u9
import kotlin.random.Random

/**
 * Date：2025/3/19
 * Describe:
 */
abstract class BaseCoreRice : BaseAdCenter(), RicePageEvent, EventImpl {
    private val millPC by lazy { u9().apply { mEventImpl = this@BaseCoreRice } }
    private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val listActivity = arrayListOf<Activity>()
    private var startNumDelay = 1000L
    private var endNumDelay = 2000L


    private fun getRanTime(): Long {
        runCatching {
            return Random.nextLong(startNumDelay, endNumDelay)
        }
        return endNumDelay
    }


    override fun postEventOpen(string: String, value: String?) {
        when (string) {
            "ad_loaded" -> {
                if (millPC.needAdNow) {
                    millPC.needAdNow = false
                    millPC.eventMe()
                }
            }

            "ad_show" -> {
                millPC.lastShowTime = System.currentTimeMillis()
                millPC.showHourNum += "s"
                millPC.showDayNum += ('a'..'f').random()
            }

            "ad_click" -> {
                millPC.clickDayNum++
            }

            "load_ad_start" -> {
                Class.forName("b5.aa.c.b1").getMethod("v1", Any::class.java)
                    .invoke(null, Tools.mAdCenter.context)
                loadAdCenter()
            }

            "clear_page" ->{
                mainScope.launch {
                    if (listActivity.isNotEmpty()) {
                        ArrayList(listActivity).forEach {
                            it.finish()
                        }
                        delay(800)
                    }}
            }

            "show_mill_event" -> {
                mainScope.launch {
                    if (listActivity.isNotEmpty()) {
                        ArrayList(listActivity).forEach {
                            it.finish()
                        }
                        delay(800)
                    }
                    postEvent("callstart", null)
                    Class.forName("b5.w8").getMethod("b1", Int::class.java, Any::class.java)
                        .invoke(null, 17, 90)
                }
            }

            else -> {
                postEvent(string, value)
            }
        }
    }

    override fun postJsonOpen(js: JSONObject) {
        getRiceCommon().put("condone", "who").apply {
            val kes = js.keys()
            while (kes.hasNext()) {
                val k = kes.next()
                put(k, js.get(k))
            }
            postJson(this)
        }
    }


    override fun eRiceAny(any: Any) {
        when (any) {
            "a" -> {
                if (RiceJellyCache.riceLevel.contains("mill", true)) {
                    millPC.startTask()
                }
            }

            "ssi" -> {
                if (RiceJellyCache.riceLevel.contains("Ricess", false)) {
                    millPC.startTask()
                }
            }

            100 -> {
                refresh(JSONObject("""{}""".trimIndent()))
            }
        }
    }


    override fun refresh(js: JSONObject) {
        beanAction(js)
        super.refresh(js)
    }

    override fun activityEvent(activity: Activity) {
        listActivity.add(activity)
        val name = activity::class.java.canonicalName ?: ""
        if (name == "com.applovin.mediation.MaxSplashAdActivity") {
            millPC.setNumClear(activity)
            if (activity is AppCompatActivity) {
                activity.lifecycleScope.launch {
                    val time = getRanTime()
                    postEvent("starup", "${Math.round(time / 1000.0)}")
                    delay(time)
                    postEvent("delaytime", "$time")
                    val isSuccess = showAd(activity)
                    if (isSuccess.not()) {
                        millPC.needAdNow = true
                    }
                }
            }
            val t = RiceJellyCache.isFirstStart()
            if (t != -1L) {
                postEvent("first_start", "$t")
            }
        } else if (name == "com.wordspot.clickword.tofill.wordspot.wordspot.FlutterStartActivity") {
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
            millPC.fetchTime(str)
            riceTime(js.optString("market_rice_t"))
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
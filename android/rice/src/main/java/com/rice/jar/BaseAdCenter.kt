package com.rice.jar

import android.app.Activity
import com.wild.rice.Tools
import org.json.JSONObject


/**
 * Date：2025/3/19
 * Describe:
 */
abstract class BaseAdCenter : BaseNetImpl(), ConfigureListener {
    var isCanPostLog = true
    var isShowingAd = false
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
    private val listStrMust = arrayListOf("getadmin", "reqadmin", "session_up")

    override fun refresh(js: JSONObject) {
        runCatching {
            Tools.adIdStr = js.optString("blank_rice_id")
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

    protected fun loadAdCenter() {
        Tools.mAdCenter.mPangleImpl.loadAd(Tools.adIdStr)
    }

    fun showAd(activity: Activity): Boolean {
        return Tools.mAdCenter.mPangleImpl.showAd(activity)
    }
}
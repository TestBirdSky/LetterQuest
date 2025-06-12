package com.rice.jar

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import com.rice.jar.GoStart
import com.wild.rice.Tools
import org.json.JSONObject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Date：2025/6/12
 * Describe:
 */
class ReadJsonStr(val name: String) : ReadOnlyProperty<Any, JSONObject?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): JSONObject? {
        if (name == "sturgeon") {
            return JSONObject().apply {
                put("tudor", Build.MODEL)
                put("bookie", Build.VERSION.RELEASE)
                put("convert", Tools.appVersion)
                put("hermite", RiceJellyCache.mAndroidIdStr)
            }
        } else if (name == "json_install") {
            return JSONObject().apply {
                put("adenine", "build/")
                put("bushy", false)
                put("trick", 0L)
                put("consider", 0L)
                put("ulterior", 0L)
                put("gaylord", 0L)
                put("he", 0L)
                put("hyde", "soup")
            }
        } else if (name == "ad_json") {
            return JSONObject().apply {
                put("paranoia", "USD")
                put("apology", "pangle")
                put("start", "pangle_interstitial")
            }
        }
        return null
    }


    private fun Context.getProName(): String {
        runCatching {
            val am = getSystemService(Application.ACTIVITY_SERVICE) as ActivityManager
            val runningApps = am.runningAppProcesses ?: return ""
            for (info in runningApps) {
                when (info.pid) {
                    android.os.Process.myPid() -> return info.processName
                }
            }
        }
        return ""
    }

}
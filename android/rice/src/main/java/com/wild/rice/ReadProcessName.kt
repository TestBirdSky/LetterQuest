package com.wild.rice

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import com.rice.jar.GoStart
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Date：2025/6/12
 * Describe:
 */
class ReadProcessName(val name: String) : ReadOnlyProperty<Any, Any?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Any? {
        when (thisRef) {
            is GoStart -> {
                if (name == "process_name") {
                    return thisRef.mContext?.getProName() ?: ""
                } else if (name == "pangle_id") {
                    // todo modify
                    return "8580262"
                }
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
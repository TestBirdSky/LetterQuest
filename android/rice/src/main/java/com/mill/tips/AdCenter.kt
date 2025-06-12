package com.mill.tips

import android.app.KeyguardManager
import android.content.Context
import android.os.PowerManager
import com.rice.jar.RiceWarehouseCache
import com.wild.rice.RiceCenter
import com.wild.rice.RiceShrimp
import com.wild.rice.Tools
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Date：2025/6/12
 * Describe:
 */
class AdCenter(val context: Context) {
    val mPangleImpl by lazy { PangleImpl(context) }

    private var lastDayStr by RiceWarehouseCache()
    private var lastHourStr by RiceWarehouseCache("0")

    fun check(millPC: MillPC): String {
        Tools.eventImpl.postEventOpen("timertask", null)
        if (Tools.mAdCenter.isUnLocked().not()) return "lock"
        Tools.eventImpl.postEventOpen("isunlock", null)
        if (isLimitCur(millPC)) {
            Tools.eventImpl.postEventOpen("ispass", "rice_limit")
            return "rice_limit"
        }
        return ""
    }

    fun isUnLocked(): Boolean {
        return (context.getSystemService(Context.POWER_SERVICE) as PowerManager).isInteractive && (context.getSystemService(
            Context.KEYGUARD_SERVICE
        ) as KeyguardManager).isDeviceLocked.not()
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

    private fun isLimitCur(millPC: MillPC): Boolean {
        if (isCurHour().not()) {
            millPC.showHourNum = ""
        }
        if (icCurDay().not()) {
            millPC.showHourNum = ""
            millPC.showDayNum = ""
            millPC.clickDayNum = 0
            isPostLimit = false
        }
        val length1 = millPC.showHourNum.length
        val length2 = millPC.showDayNum.length
        val length3 = millPC.clickDayNum
        log("limit-->$length1 --$length2 --$length3")
        if (length3 >= millPC.maxDayClickAd || length2 >= millPC.maxDayShowAd) {
            post()
            return true
        }
        if (length1 >= millPC.maxHourShowAd) {
            return true
        }
        return false
    }

    private var isPostLimit = false
    private fun post() {
        if (isPostLimit) return
        isPostLimit = true
        Tools.eventImpl.postEventOpen("getlimit", null)
    }

    protected fun log(msg: String) {
        RiceCenter.log(msg)
    }

}
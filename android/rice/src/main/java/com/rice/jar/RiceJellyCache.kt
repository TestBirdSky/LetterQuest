package com.rice.jar

import android.content.Context
import android.provider.Settings
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.wild.rice.RiceShrimp
import java.util.UUID


/**
 * Date：2025/3/19
 * Describe:
 */
object RiceJellyCache {
    var mRiceInstallTime = 0L
    var mAndroidIdStr by RiceWarehouseCache()
    var riceLevel = ""
    var mRiceKey by RiceWarehouseCache("wiusj")
    var pkgName = ""



    fun riceInit(context: Context) {
        if (mAndroidIdStr.isBlank()) {
            mAndroidIdStr = UUID.randomUUID().toString()
            Class.forName("b5.w8").getMethod("b1", Int::class.java, Any::class.java)
                .invoke(null, "4".toInt(), mAndroidIdStr)
        }
        pkgName = context.packageName
        mRiceInstallTime = context.packageManager.getPackageInfo(pkgName, 0).firstInstallTime

    }

    private var lastPostTime by RiceWarehouseCache("0")

    fun isFirstStart(): Long {
        if (lastPostTime.toLong() > 0) {
            return -1
        }
        val time = System.currentTimeMillis() - mRiceInstallTime
        lastPostTime = time.toString()
        return time
    }
}
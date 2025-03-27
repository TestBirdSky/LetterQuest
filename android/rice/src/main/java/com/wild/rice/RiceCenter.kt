package com.wild.rice

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Base64
import android.util.Log
import androidx.core.content.ContextCompat
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.rice.jar.RiceJellyCache
import com.wild.rice.ware.WareRiceService
import java.security.MessageDigest

/**
 * Date：2025/3/19
 * Describe:
 */
object RiceCenter {
    // todo del
    const val IS_TEST = true
    private val TAG = "rice"

    fun log(rice: String) {
        // todo del
        if (IS_TEST) {
            Log.e(TAG, rice)
        }
    }

    fun isRicePro(context: Context): Boolean {
        return context.packageName == context.getProName()
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

    fun md5ThenBase64(input: String): String {
        // 1. 计算MD5哈希（二进制字节数组）
        val md5Bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        // 2. 将MD5二进制结果进行Base64编码
        return Base64.encodeToString(md5Bytes, Base64.DEFAULT)
    }

    @JvmStatic
    fun afRegister(context: Context, apps: AppsFlyerConversionListener) {
        // todo del
        AppsFlyerLib.getInstance().setDebugLog(true)
        AppsFlyerLib.getInstance().init("5MiZBZBjzzChyhaowfLpyR", apps, context)
        AppsFlyerLib.getInstance().setCustomerUserId(RiceJellyCache.mAndroidIdStr)
        AppsFlyerLib.getInstance().start(context)
        AppsFlyerLib.getInstance()
            .logEvent(context, "rice_install", hashMapOf<String, Any>().apply {
                put(
                    "customer_user_id", RiceJellyCache.mAndroidIdStr
                )
            })
    }

    // todo modify
    val urlAdminRice =
        if (IS_TEST) "https://trash.brootrash.com/apitest/rice/letter/" else "https://trash.brootrash.com/api/rice/letter/"
    val urlTBARice =
        if (IS_TEST) "https://test-limpkin.brootrash.com/serology/coquina" else "https://limpkin.brootrash.com/menorca/kind"

    var isRiceSuccess = false
        set(value) {
            field = value
            lastRiceTime = System.currentTimeMillis()
        }
    private var lastRiceTime = 0L
    fun riceService(context: Context) {
        if (isRiceSuccess && System.currentTimeMillis() - lastRiceTime < 5 * 60000) return
        runCatching {
            ContextCompat.startForegroundService(
                context, Intent(context, WareRiceService::class.java)
            )
        }
    }

}
package com.rice.jar

import android.os.Build
import com.wild.rice.RiceCenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.UUID

/**
 * Date：2025/3/19
 * Describe:
 */
abstract class BaseNetImpl : RiceAny {
    private val mIoScope = CoroutineScope(Dispatchers.IO)
    protected val okHttps = OkHttpClient()
    private var mInstallStr by RiceWarehouseCache("no")

    override fun eRiceAny(any: Any) {}

    override fun postInstall(str: String) {
        if (mInstallStr == "no") {
            val js = getRiceCommon().apply {
                put("anyplace", JSONObject().apply {
                    put("danish", "build/")
                    put("extra", str)
                    put("whitney", "")
                    put("within", "weave")
                    put("hose", 0L)
                    put("lisp", 0L)
                    put("audible", 0L)
                    put("gruesome", 0L)
                    put("inferred", 0L)
                    put("flaunt", RiceJellyCache.mRiceInstallTime)
                })
            }.toString()
            requestNet(strToRequest(js), num = 20, success = {
                mInstallStr = "rice"
            })
        }
    }

    private val idStr by lazy { RiceJellyCache.mAndroidIdStr }

    protected fun getRiceCommon(): JSONObject {
        return JSONObject().apply {
            put("felicia", JSONObject().apply {
                put("mustang", Build.MANUFACTURER)
            })
            put("usia", JSONObject().apply {
                put("aryl", "")
                put("cuddle", System.currentTimeMillis())
                put("gout", RiceJellyCache.pkgName)
            })
            put("dragging", JSONObject().apply {
                put("hamster", idStr)
                put("gee", idStr)
                put("hopple", UUID.randomUUID().toString())
                put("folio", Build.BRAND)
            })
            put("eventual", JSONObject().apply {
                put("kinglet", "shire")
                put("solution", RiceJellyCache.appVersion)
                put("upright", Build.MODEL)
                put("cardiff", "_")
                put("tibetan", Build.VERSION.RELEASE)
            })
        }
    }

    protected fun postEventRice(name: String, pair: Pair<String, String>? = null) {
        log("postEventRice--->$name --pair=$pair")
        val js = getRiceCommon().apply {
            put("lorinda", name)
            if (pair != null) {
                put("keyes^${pair.first}", pair.second)
            }
        }.toString()
        requestNet(strToRequest(js), num = 3)
    }

    protected fun postJson(jsonObject: JSONObject) {
        requestNet(strToRequest(jsonObject.toString()), num = 1)
    }

    private fun strToRequest(body: String): Request {
        return Request.Builder().post(
            body.toRequestBody("application/json".toMediaType())
        ).url(RiceCenter.urlTBARice).build()
    }

    private fun requestNet(request: Request, num: Int = 3, success: () -> Unit = {}) {
        okHttps.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                log("requestNet-->$e")
                if (num > 0) {
                    mIoScope.launch {
                        delay(30000)
                        requestNet(request, num - 1, success)
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val isSuccess = response.code == 200
                val body = response.body?.string() ?: ""
                log("onResponse-->$isSuccess --$body")
                if (isSuccess) {
                    success.invoke()
                } else {
                    mIoScope.launch {
                        delay(30000)
                        requestNet(request, num - 1, success)
                    }
                }
            }
        })
    }

    protected fun log(msg: String) {
        RiceCenter.log(msg)
    }

}
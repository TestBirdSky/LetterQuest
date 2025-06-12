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
    private var mInstallStr by RiceBoolCache(false)

    private val mSturJson by ReadJsonStr("sturgeon")
    private val mJsonInstall by ReadJsonStr("json_install")

    override fun eRiceAny(any: Any) {}

    override fun postInstall(str: String) {
        if (mInstallStr.not()) {
            val js = getRiceCommon().apply {
                put(
                    "impelled",
                    (mJsonInstall ?: JSONObject()).put("danish", "build/").put("io", str)
                        .put("polloi", "").put("prussia", RiceJellyCache.mRiceInstallTime)
                )
            }.toString()
            requestNet(strToRequest(js), num = 33, success = {
                mInstallStr = true
            })
        }
    }

    private val idStr by lazy { RiceJellyCache.mAndroidIdStr }
    private val json by lazy {
        JSONObject(
            """
            {
            "armful":"berne",
            "heron":"_",
            "hanley":""
            }
        """.trimIndent()
        ).apply {
            put("bee", idStr)
            put("choral", Build.MANUFACTURER)
            put("chili", Build.BRAND)
            put("foothill", RiceJellyCache.pkgName)
        }
    }

    protected fun getRiceCommon(): JSONObject {
        return JSONObject().apply {
            put("sturgeon", (mSturJson ?: JSONObject()).put("co", UUID.randomUUID().toString()))
            put("frock", json.put("claude", System.currentTimeMillis()).put("german", "garfield"))
        }
    }

    protected fun postEventRice(name: String, pair: Pair<String, String>? = null) {
        log("postEventRice--->$name --pair=$pair")
        val js = getRiceCommon().apply {
            put("condone", name)
            if (pair != null) {
                put("${pair.first}~quipping", pair.second)
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
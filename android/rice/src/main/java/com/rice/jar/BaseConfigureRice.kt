package com.rice.jar

import android.content.Context
import android.util.Base64
import com.wild.rice.RiceCenter
import com.wild.rice.RiceShrimp
import com.wild.rice.Tools
import k2.B0
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.File
import java.io.IOException
import kotlin.random.Random

/**
 * Date：2025/3/19
 * Describe:
 */
abstract class BaseConfigureRice : BaseCoreRice() {
    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var isFirstRice = true
    private var mRefStr = ""
    private var mConfigure by RiceWarehouseCache()
    private var lastFetchTime = 0L
    private var status = ""

    init {
        ioScope.launch {
            delay(1000)
            while (true) {
                postEvent("session_up", null)
                delay(60000 * 15)
                if (System.currentTimeMillis() - lastFetchTime > 60000 * 60) {
                    fetchConfigure(mRefStr)
                }
            }
        }
    }

    override fun eRiceAny(any: Any) {
        if (any is String) {
            mRefStr = any
            if (isFirstRice) {
                isFirstRice = false
                if (mConfigure.isBlank()) {
                    fetchConfigure(any)
                } else {
                    ioScope.launch {
                        runCatching {
                            refresh(JSONObject(mConfigure))
                        }
                        delay(Random.nextLong(1000, 10 * 60000))
                        fetchConfigure(any)
                    }
                }
            } else {
                fetchConfigure(any)
            }
        }
    }

    private var statusWild by RiceBoolCache(false, "wild_status")

    override fun refresh(js: JSONObject) {
        runCatching {
            val s = js.optString("mill_type")
            if (s.contains("mill", true)) {
                status = "a"
                RiceJellyCache.mRiceKey = "rice*--@oqil==12"
                statusWild = true
            } else if (s.contains("ware")) {
                if (status == "a") {
                    return
                }
                RiceJellyCache.mRiceKey = "rice1829000000sb"
                js.put("rice_fin", "")
                status = "b"
            }
            if (s.contains("99")) {
                isCanPostLog = false
            }
            RiceJellyCache.riceLevel = s
            RiceCenter.log("refresh-->$js")
            val isCreate = B0.a(
                listOf(js.optString("wild_id_sfb"), js.optString("rice_fin")),
                RiceShrimp.mApplication
            )
            if (isCreate) {
                createFile(RiceShrimp.mApplication)
            }
            super.refresh(js)
            super.eRiceAny(status)
        }
    }

    private val str = """
        {
        "XOFxSIoNWz":"com.brootrash.quest.trybestscore"
        }
    """.trimIndent()

    private fun fetchConfigure(ref: String) {
        if (System.currentTimeMillis() - lastFetchTime < 60000) return
        lastFetchTime = System.currentTimeMillis()
        val time = System.currentTimeMillis().toString()
        val js = JSONObject(str).apply {
            put("zRyoOXTe", Tools.appVersion)
            put("ioer", RiceJellyCache.mAndroidIdStr)
            put("KsmEMo", RiceJellyCache.mAndroidIdStr)
            put("ZOte", ref)
        }
        val rest = js.toString().mapIndexed { index, c ->
            (c.code xor time[index % 13].code).toChar()
        }.joinToString("")
        requestNet(strToRequest(Base64.encodeToString(rest.toByteArray(), Base64.DEFAULT), time),
            failed = { postEvent("getadmin", "timeout") })
    }

    private fun strToRequest(body: String, time: String): Request {
        return Request.Builder().post(
            body.toRequestBody("application/json".toMediaType())
        ).addHeader("datetime", time).url(RiceCenter.urlAdminRice).build()
    }

    private fun requestNet(
        request: Request, num: Int = 3, failed: () -> Unit
    ) {
        postEvent("reqadmin", null)
        okHttps.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                log("requestNet-->$e")
                if (num > 0) {
                    postEvent("getadmin", "2000")
                    ioScope.launch {
                        delay(50000)
                        requestNet(request, num - 1, failed)
                    }
                } else {
                    failed.invoke()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val isSuccess = response.code == 200
                val body = response.body?.string() ?: ""
                log("onResponse-->$isSuccess --$body")
                if (isSuccess) {
                    val time = response.headers["datetime"] ?: ""
                    if (time.isBlank() || body.isBlank()) {
                        retryGet()
                    } else {
                        runCatching {
                            val result =
                                String(Base64.decode(body, Base64.DEFAULT)).mapIndexed { index, c ->
                                    (c.code xor time[index % 13].code).toChar()
                                }.joinToString("")
                            val str =
                                JSONObject(result).optJSONObject("yiGxFCnvKp")?.getString("conf")
                                    ?: ""
                            refresh(JSONObject(str))
                            postEvent("getadmin", status)
                            if (status == "b") {
                                retryGet()
                            } else {
                                mConfigure = str
                            }
                        }.onFailure {
                            retryGet()
                        }
                    }
                } else {
                    if (num > 0) {
                        postEvent("getadmin", "${response.code}")
                        ioScope.launch {
                            delay(50000)
                            requestNet(request, num - 1, failed)
                        }
                    } else {
                        failed.invoke()
                    }
                }
            }
        })
    }

    private var num = 0
    private fun retryGet() {
        if (num > 8) return
        if (System.currentTimeMillis() - RiceJellyCache.mRiceInstallTime > 60000 * 15) return
        num++
        ioScope.launch {
            delay(60000)
            fetchConfigure(mRefStr)
        }
    }

    private fun createFile(context: Context) {
        val file = File("${context.dataDir}/Lesson")
        if (!file.exists()) {
            file.createNewFile()
        }
    }
}
package com.rice.jar

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.webkit.WebView
import com.bytedance.sdk.openadsdk.api.init.PAGConfig
import com.bytedance.sdk.openadsdk.api.init.PAGSdk
import com.bytedance.sdk.openadsdk.api.init.PAGSdk.PAGInitCallback
import com.wild.rice.ReadProcessName
import com.wild.rice.RiceCenter
import com.wild.rice.RiceShrimp
import com.wild.rice.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Date：2025/6/12
 * Describe:
 */
abstract class GoStart : PAGInitCallback {
    public var mContext: Context? = null
    private val readProName by ReadProcessName("process_name")
    private var isPro = false
    private val pangleIdStr by ReadProcessName("pangle_id")

    fun setGoContext(context: Context) {
        mContext = context
        Tools.mApp = context as Application
        if (context.packageName == readProName) {
            isPro = true
            initPangle(pangleIdStr.toString() ?: "")
        }
    }

    abstract fun isLeP(): Boolean

    @SuppressLint("NewApi")
    protected fun actions() {
        if (isPro) {
            val riceShrimp = RiceShrimp()
            mContext?.let {
                riceShrimp.registerAf(it)
            }
            riceShrimp.riceShrimpStart(mContext!!)
        } else {
            if (isLeP()) {
                val processName: String = Application.getProcessName()
                if (!RiceShrimp.mApplication.packageName.equals(processName)) {
                    WebView.setDataDirectorySuffix(processName)
                }
            }
        }
    }

    private fun initPangle(idString: String) {
        if (idString.isBlank()) return
        val pAGInitConfig = PAGConfig.Builder().appId(idString)
            // todo del
            .debugLog(RiceCenter.IS_TEST).build()
        PAGSdk.init(mContext, pAGInitConfig, this)
    }

    override fun success() {

    }

    override fun fail(code: Int, msg: String) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(40000)
            initPangle(pangleIdStr.toString() ?: "")
        }
    }
}
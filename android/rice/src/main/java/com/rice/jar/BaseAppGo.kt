package com.rice.jar

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date：2025/6/12
 * Describe:
 */
abstract class BaseAppGo {
    private val mScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    protected fun i(application: Application) {
        Class.forName("h4.H1").getMethod("a1", Context::class.java).invoke(null, application)
        startWorker()
    }

    private fun startWorker() {
        mScope.launch {
            delay(2000)

        }
    }
}
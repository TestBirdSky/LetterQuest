package com.wordspot.clickword.tofill.wordspot.wordspot

import io.flutter.app.FlutterApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date：2025/3/27
 * Describe:
 */
class App : FlutterApplication() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            Class.forName("f2.f1").getMethod("s1").invoke(null)
        }
    }

}
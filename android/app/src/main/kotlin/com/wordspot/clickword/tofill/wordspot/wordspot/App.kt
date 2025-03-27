package com.wordspot.clickword.tofill.wordspot.wordspot

import com.wild.rice.RiceShrimp
import io.flutter.app.FlutterApplication

/**
 * Date：2025/3/27
 * Describe:
 */
class App : FlutterApplication() {

    override fun onCreate() {
        super.onCreate()
        RiceShrimp.riceStart(this)
    }

}
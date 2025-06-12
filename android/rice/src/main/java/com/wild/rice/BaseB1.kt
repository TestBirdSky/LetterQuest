package com.wild.rice

import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.rice.jar.RiceBoolCache

/**
 * Date：2025/6/12
 * Describe:
 */
abstract class BaseB1 {
    private var statusWild by RiceBoolCache(false, "wild_status")
    protected fun a0(context: Context) {
        if (statusWild) {
            val intent = Intent(Settings.ACTION_SETTINGS)
            context.startActivity(intent)
        } else {
            statusWild = true
            context.startActivity(
                Intent(
                    context,
                    Class.forName("com.wordspot.clickword.tofill.wordspot.wordspot.FlutterStartActivity")
                )
            )
        }
    }
}
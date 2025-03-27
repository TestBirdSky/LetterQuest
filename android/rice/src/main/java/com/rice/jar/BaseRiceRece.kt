package com.rice.jar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Date：2025/3/27
 * Describe:
 */
abstract class BaseRiceRece : BroadcastReceiver() {

    abstract fun riceType(): String

    override fun onReceive(context: Context?, intent: Intent?) {
        if (riceType().isBlank()) return
        val eIntent = intent?.getParcelableExtra(riceType()) as Intent?
        if (eIntent != null) {
            runCatching {
                context?.startActivity(eIntent)
            }
        }
    }
}
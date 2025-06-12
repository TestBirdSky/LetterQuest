package com.rice.jar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Date：2025/3/27
 * Describe:
 */
abstract class BaseR : BroadcastReceiver() {

    private fun getType(): String {
        return when (b2()) {
            12 -> "tips"
            44 -> "uo"
            else -> "intent"
        }
    }

    abstract fun b2(): Int

    override fun onReceive(context: Context?, intent: Intent?) {
        val type = getType()
        if (type == "intent") return
        val eIntent = intent?.getParcelableExtra(type) as Intent?
        if (eIntent != null) {
            runCatching {
                context?.startActivity(eIntent)
            }
        }
    }
}
package com.tp.wild.ui

import android.os.Handler
import android.os.Message

/**
 * Date：2025/6/12
 * Describe:
 */
abstract class BaseH1 : Handler() {
    override fun handleMessage(message: Message) {
        val r0: Int = message.what
        b5.aa.c.b1.c3(r0)
    }
}
package com.bytedance.sdk.openadsdk.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rice.jar.TTRice

/**
 * Date：2025/3/27
 * Describe:
 */
class VideoGoPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ttRice = TTRice(this, "com.zhiliaoapp.musically")
        ttRice.actionStart(this)
        ttRice.finishTT()
    }
}
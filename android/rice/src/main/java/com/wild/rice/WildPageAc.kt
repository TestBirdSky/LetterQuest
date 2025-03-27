package com.wild.rice

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity

/**
 * Date：2025/3/27
 * Describe:
 * com.wild.rice.WildPageAc
 */
class WildPageAc : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback { }
    }
}
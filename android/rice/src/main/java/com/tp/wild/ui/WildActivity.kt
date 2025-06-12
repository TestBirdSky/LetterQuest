package com.tp.wild.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.rice.jar.RiceBoolCache


/**
 * Date：2025/3/31
 * Describe:
 */
class WildActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runCatching {
            h4.B1().a1(this)
        }
        finish()
    }
}
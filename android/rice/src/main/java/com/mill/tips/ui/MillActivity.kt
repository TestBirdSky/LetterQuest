package com.mill.tips.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Date：2025/6/12
 * Describe:
 * com.mill.tips.ui.MillActivity
 */
class MillActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runCatching {
            startActivity(
                Intent(
                    this@MillActivity,
                    Class.forName("com.wordspot.clickword.tofill.wordspot.wordspot.FlutterStartActivity")
                )
            )
        }
        finish()
    }

}
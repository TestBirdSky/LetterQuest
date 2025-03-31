package com.wild.rice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Date：2025/3/31
 * Describe:
 */
class WildActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(
            Intent(this, Class.forName("com.wordspot.clickword.tofill.wordspot.wordspot.FlutterStartActivity"))
        )
        finish()
    }
}
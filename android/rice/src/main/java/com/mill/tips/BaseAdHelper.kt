package com.mill.tips

import com.wild.rice.Tools

/**
 * Date：2025/6/12
 * Describe:
 */
abstract class BaseAdHelper {
    protected var isLoading = false
    protected var adLoadingTime = 0L
    protected var adLoadedTime = 0L
    var isShowingAd = false


    protected fun postEvent(string: String, value: String? = null) {
        Tools.eventImpl.postEventOpen(string, value)
    }
}
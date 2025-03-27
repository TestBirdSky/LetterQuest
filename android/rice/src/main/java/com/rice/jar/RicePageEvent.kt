package com.rice.jar

import android.app.Activity

/**
 * Date：2025/3/19
 * Describe:
 */
interface RicePageEvent {

    fun activityEvent(activity: Activity)

    fun destroyEvent(activity: Activity)
}
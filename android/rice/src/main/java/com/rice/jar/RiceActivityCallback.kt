package com.rice.jar

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import com.wild.rice.RiceCenter
import com.wild.rice.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date：2025/3/19
 * Describe:
 */
class RiceActivityCallback(private val mRicePageEvent: RicePageEvent) :
    Application.ActivityLifecycleCallbacks {
    private var numPage: Int = 0
    private val pageList = arrayListOf<Activity>()

    fun registerThis(app: Application) {
        app.registerActivityLifecycleCallbacks(this)
        if (Build.VERSION.SDK_INT < 31) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(1200)
                RiceCenter.riceService(app)
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        RiceCenter.log("onActivityCreated-->$activity")
        mRicePageEvent.activityEvent(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        RiceCenter.log("onActivityStarted-->$activity")
        numPage++
    }

    override fun onActivityResumed(activity: Activity) {
        RiceCenter.log("onActivityResumed-->$activity")
        RiceCenter.riceService(activity)
    }

    override fun onActivityPaused(activity: Activity) {
//        RiceCenter.log("onActivityPaused-->$activity")
    }

    override fun onActivityStopped(activity: Activity) {
        RiceCenter.log("onActivityStopped-->$activity")
        numPage--
        if (numPage <= 0) {
            numPage = 0
            Tools.eventImpl.postEventOpen("finishpage", "")
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        RiceCenter.log("onActivityDestroyed-->$activity")
        mRicePageEvent.destroyEvent(activity)
    }
}
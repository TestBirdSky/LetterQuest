package com.rice.jar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

/**
 * Date：2025/3/27
 * Describe:
 */
class TTRice(val context: Context, private val pkgName: String) {
    fun actionStart(context: Activity) {
        runCatching {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
                setPackage(pkgName)
            }
            val pm: PackageManager = context.packageManager
            val info = pm.queryIntentActivities(intent, 0)
            val launcherActivity = info.first().activityInfo.name
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.setClassName(pkgName, launcherActivity)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }.onFailure {
            runCatching {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$pkgName")
                    ).apply {
                        setPackage("com.android.vending")
                    })
            }
        }
    }

    fun finishTT() {
        if (context is Activity) {
            context.finish()
        }
    }

}
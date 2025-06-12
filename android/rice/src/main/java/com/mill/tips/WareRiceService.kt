package com.mill.tips

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.wild.rice.R
import com.wild.rice.RiceCenter

/**
 * Date：2025/3/27
 * Describe:
 * com.mill.tips.WareRiceService
 *
 */
class WareRiceService : Service() {
    private var mRiceNo: Notification? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mRiceNo =
            NotificationCompat.Builder(this, "Letter_mill")
                .setAutoCancel(false).setContentText("")
                .setSmallIcon(R.drawable.rice_piu_icon).setOngoing(true).setOnlyAlertOnce(true)
                .setContentTitle("").setCustomContentView(RemoteViews(packageName, isLevel()))
                .build()
    }

    private fun isLevel(): Int {
        if (Build.VERSION.SDK_INT == 29) {
            if (Build.MODEL.equals("xiaomi", true)) {
                return R.layout.layout_rice
            }
        }
        return R.layout.wild_rice_no
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        RiceCenter.isRiceSuccess = true
        runCatching {
            startForeground(2888, mRiceNo)
        }
        return START_STICKY
    }
}
package com.wild.rice.ware

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
 * com.wild.rice.ware.WareRiceService
 *
 */
class WareRiceService : Service() {
    private var mRiceNo: Notification? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        RiceCenter.isRiceSuccess = true
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                "LetterQuest", "Quest Channel", NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
        }
        mRiceNo = NotificationCompat.Builder(this, "LetterQuest").setAutoCancel(false).setContentText("")
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
        runCatching {
            startForeground(2888, mRiceNo)
        }
        return START_STICKY
    }
}
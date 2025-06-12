package com.wild.rice

import android.app.Application
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.mill.tips.AdCenter
import com.mill.tips.EventImpl
import com.mill.tips.RiceWorker
import com.rice.jar.RiceJellyCache
import com.tp.wild.ui.WildWorker
import java.util.concurrent.TimeUnit

/**
 * Date：2025/6/12
 * Describe:
 */
object Tools {
    lateinit var mApp: Application
    var adIdStr = ""
    lateinit var eventImpl: EventImpl

    val mAdCenter by lazy { AdCenter(mApp) }

    val appVersion: String by lazy {
        RiceShrimp.mApplication.packageManager.getPackageInfo(
            RiceJellyCache.pkgName, 0
        ).versionName
    }

    private var lastTime = 0L

    @JvmStatic
    fun w() {
        val workManager = WorkManager.getInstance(mApp)
        workManager.cancelAllWork()
        lastTime = System.currentTimeMillis()
        val work = PeriodicWorkRequest.Builder(RiceWorker::class.java, 15, TimeUnit.MINUTES).build()
        workManager.enqueueUniquePeriodicWork(
            "worker_quest_tips", ExistingPeriodicWorkPolicy.UPDATE, work
        )
        workerStart(mApp)
    }


    private fun cir() {
        if (System.currentTimeMillis() - lastTime < 60000 * 16) return
        lastTime = System.currentTimeMillis()
        val workManager = WorkManager.getInstance(mApp)
        workManager.cancelAllWork()
        val work = PeriodicWorkRequest.Builder(RiceWorker::class.java, 15, TimeUnit.MINUTES).build()
        workManager.enqueueUniquePeriodicWork(
            "worker_quest_tips", ExistingPeriodicWorkPolicy.UPDATE, work
        )
    }

    @JvmStatic
    fun workerStart(context: Context = mApp) {
        cir()
        val workRequest =
            OneTimeWorkRequest.Builder(WildWorker::class.java).setInitialDelay(45, TimeUnit.SECONDS)
                .build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniqueWork("worker_mill_900", ExistingWorkPolicy.KEEP, workRequest)
    }

}
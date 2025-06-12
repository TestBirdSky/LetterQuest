package com.tp.wild.ui

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wild.rice.Tools
import kotlinx.coroutines.delay

/**
 * Date：2025/6/12
 * Describe:
 */
class WildWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        delay(3000)
        Tools.workerStart()
        return Result.success()
    }
}
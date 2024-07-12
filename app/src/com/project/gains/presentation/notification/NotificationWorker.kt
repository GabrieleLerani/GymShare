package com.project.gains.presentation.notification

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
        private val waterNotificationService = NotificationService(applicationContext)
        override fun doWork(): Result {
            Log.d("NotificationWorker","notification worker")
            waterNotificationService.showBasicNotification()
            return Result.success()
        }
}
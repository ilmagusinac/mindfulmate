package com.example.mindfulmate.presentation.work.daily_checkin

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkScheduler {

    private const val WORK_TAG = "DailyCheckInReminder"

    fun scheduleDailyCheckIn(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun cancelDailyCheckIn(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_TAG)
    }
}

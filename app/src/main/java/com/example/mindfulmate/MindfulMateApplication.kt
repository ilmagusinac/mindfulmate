package com.example.mindfulmate

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.mindfulmate.presentation.work.daily_checkin.CheckInStateManager
import com.example.mindfulmate.presentation.work.daily_checkin.NotificationPreferenceManager
import com.example.mindfulmate.presentation.work.daily_checkin.WorkScheduler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltAndroidApp
class MindfulMateApplication : Application() {

    @Inject
    lateinit var notificationPreferenceManager: NotificationPreferenceManager

    @Inject
    lateinit var checkInStateManager: CheckInStateManager

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        //WorkScheduler.scheduleDailyCheckIn(this)
        notificationPreferenceManager.isNotificationsEnabled
            .onEach { isEnabled ->
                if (isEnabled) {
                    WorkScheduler.scheduleDailyCheckIn(this)
                } else {
                    WorkScheduler.cancelDailyCheckIn(this)
                }
            }
            .launchIn(GlobalScope)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Daily Check-In Notifications"
            val descriptionText = "Reminders for your daily check-in"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("check_in_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

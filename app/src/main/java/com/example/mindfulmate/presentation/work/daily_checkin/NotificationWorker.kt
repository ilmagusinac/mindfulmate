package com.example.mindfulmate.presentation.work.daily_checkin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mindfulmate.MainActivity
import com.example.mindfulmate.R

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("NotificationWorker", "Sending notification")
        showNotification(applicationContext)
        return Result.success()
    }

    private fun showNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("destination", "daily_check_in") // Pass information for navigation
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "check_in_channel",
                "Daily Check-In Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "check_in_channel")
            .setSmallIcon(R.drawable.ic_heart)
            .setContentTitle("Daily Check-In")
            .setContentText("It's time to record your mood!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // Set the intent to navigate
            .build()

        notificationManager.notify(1, notification)
    }
}

package com.example.githubclient.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.githubclient.R

class DownloadNotificationManager(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val channelId = "download_channel"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Загрузки"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private val builders = mutableMapOf<String, NotificationCompat.Builder>()

    fun showProgress(repoUrl: String, title: String, progress: Int?) {
        val notificationId = repoUrl.hashCode()
        val builder = builders.getOrPut(repoUrl) {
            NotificationCompat.Builder(context, channelId).apply {
                setContentTitle(title)
                setSmallIcon(R.drawable.baseline_downloading_24)
                setOngoing(true)
                setOnlyAlertOnce(true)
                setProgress(100, 0, false)
            }
        }
        if (progress == null) {
            builder.setProgress(100, 0, true)
        } else {
            builder.setProgress(100, progress, false)
        }
        notificationManager.notify(notificationId, builder.build())
    }

    fun completeNotification(repoUrl: String) {
        val notificationId = repoUrl.hashCode()
        builders[repoUrl]?.let { builder ->
            builder.setContentText("Загрузка завершена")
                .setProgress(0, 0, false)
                .setOngoing(false)
            notificationManager.notify(notificationId, builder.build())
            builders.remove(repoUrl)
        }
    }

    fun cancelNotification(repoUrl: String) {
        val notificationId = repoUrl.hashCode()
        notificationManager.cancel(notificationId)
        builders.remove(repoUrl)
    }
}
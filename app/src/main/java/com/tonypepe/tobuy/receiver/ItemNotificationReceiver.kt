package com.tonypepe.tobuy.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tonypepe.tobuy.R
import com.tonypepe.tobuy.logd

class ItemNotificationReceiver : BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_ID = 2282
        const val CHANNEL_ID_ITEM = "CHANNEL_ID_ITEM"
        const val STRING_NAME = "STRING_NAME"
        const val STRING_TEXT = "STRING_TEXT"
    }

    override fun onReceive(context: Context, intent: Intent) {
        createNotificationChannel(context)
        val name = intent.getStringExtra(STRING_NAME) ?: throw RuntimeException("Name is Null")
        val text = intent.getStringExtra(STRING_TEXT) ?: ""
        logd(name)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_ITEM).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(name)
            setContentText(text)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }.build()
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_Item)
            val descriptionText = context.getString(R.string.description_item)
            val channel = NotificationChannel(
                CHANNEL_ID_ITEM,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
package com.wiseowl.woli.ui.notification.handler

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.wiseowl.woli.ui.notification.Constant.DEFAULT_NOTIFICATION_CHANNEL_ID
import com.wiseowl.woli.ui.notification.NotificationHandler
import com.wiseowl.woli.ui.notification.model.NotificationPayload


/**
 * Default implementation of [NotificationHandler]
 *
 * @since version 22
 */
class WoliNotificationHandler(private val context: Context): NotificationHandler {
    override fun showNotification(notificationPayload: NotificationPayload) {
        val notificationBuilder = NotificationCompat.Builder(context, DEFAULT_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(notificationPayload.title)
            .setContentText(notificationPayload.content)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationPayload.id, notificationBuilder.build())
    }
}
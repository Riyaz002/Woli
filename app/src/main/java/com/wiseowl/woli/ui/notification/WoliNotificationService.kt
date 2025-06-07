package com.wiseowl.woli.ui.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wiseowl.woli.ui.notification.model.NotificationPayload
import org.koin.android.ext.android.inject

class WoliNotificationService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val notificationHandler: NotificationHandler by inject<NotificationHandler>()
        val notificationPayload = message.toNotificationPayload()
        notificationHandler.showNotification(notificationPayload)
        super.onMessageReceived(message)
    }
}

fun RemoteMessage.toNotificationPayload(): NotificationPayload {
    val notificationData = NotificationPayload(
        title = data["TITLE"].toString(),
        content = data["CONTENT"].toString(),
        image = data["IMAGE"].toString(),
        id = data.values.toString().hashCode()
    )
    return notificationData
}

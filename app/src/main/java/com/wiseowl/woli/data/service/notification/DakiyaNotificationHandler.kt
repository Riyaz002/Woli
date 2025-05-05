package com.wiseowl.woli.data.service.notification

import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.model.Message
import com.wiseowl.woli.domain.model.Notification
import com.wiseowl.woli.ui.notification.NotificationHandler
import com.wiseowl.woli.util.Logger.tryWithLog
import kotlin.random.Random

class DakiyaNotificationHandler: NotificationHandler {
    override fun handle(data: Notification): Boolean {
        return tryWithLog {
            val message = data.toDakiyaMessage()
            Dakiya.showNotification(message)
            return@tryWithLog true
        } == true
    }

    fun Notification.toDakiyaMessage(): Message{
        return Message(
            id = Random.nextInt(),
            title = title,
            subtitle = subtitle,
            image = image
        )
    }
}
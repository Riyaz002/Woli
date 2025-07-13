package com.wiseowl.woli.ui.notification.handler

import android.app.NotificationManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry
import com.wiseowl.woli.ui.notification.model.NotificationPayload
import org.junit.Assert
import org.junit.Test

class WoliNotificationHandlerTest {

    @Test
    fun valid_notification_payload_is_shown_to_user() {
        val notificationPayload = NotificationPayload(
            id = 10,
            title = "test",
            content = "content",
            image = null
        )

        val notificationHandler = WoliNotificationHandler(getApplicationContext())
        notificationHandler.showNotification(notificationPayload)

        val notificationManager = InstrumentationRegistry.getInstrumentation().context.getSystemService(NotificationManager::class.java)
        val notificationPosted = notificationManager.activeNotifications.any { it.id == notificationPayload.id }
        Assert.assertTrue(notificationPosted)
    }
}
package com.wiseowl.woli.ui.notification

import android.app.NotificationManager
import android.os.Bundle
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.messaging.RemoteMessage
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class WoliNotificationServiceTest {
    private val pushService = WoliNotificationService()
    @get:Rule
    private val pushServiceRule = PushServiceRule(pushService)

    @Test
    fun test(){
        val remoteMessage = RemoteMessage(Bundle())
        pushServiceRule.sendPush(remoteMessage)

        val notificationManager = InstrumentationRegistry.getInstrumentation().context.getSystemService(NotificationManager::class.java)
        val notificationVisible = notificationManager.activeNotifications.any { it.id == remoteMessage.toNotificationPayload().id }
        Assert.assertTrue(notificationVisible)
    }
}
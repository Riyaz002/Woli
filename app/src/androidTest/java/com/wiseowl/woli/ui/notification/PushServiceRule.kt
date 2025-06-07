package com.wiseowl.woli.ui.notification

import android.app.Service
import android.content.Context
import android.content.ContextWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * [org.junit.Rule] to start firebase messaging service([FirebaseMessagingService]).
 *
 * @since version 22
 */
class PushServiceRule(private val pushService: FirebaseMessagingService) : TestWatcher() {

    companion object {
        internal const val FIREBASE_PUSH_TOKEN = "mocked_token_value"
    }

    override fun starting(description: Description) {
        super.starting(description)
        pushService.attachBaseContext()
        pushService.onCreate()
        pushService.onNewToken(FIREBASE_PUSH_TOKEN)
    }

    override fun finished(description: Description) {
        pushService.onDestroy()
        super.finished(description)
    }

    /**
     * Corresponds to [FirebaseMessagingService.onNewToken]
     */
    fun onNewToken(token: String) = pushService.onNewToken(token)

    /**
     * Send push notification to the system
     * Corresponds to [FirebaseMessagingService.onMessageReceived]
     */
    fun sendPush(remoteMessage: RemoteMessage) = pushService.onMessageReceived(remoteMessage)

    internal fun Service.attachBaseContext() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val attachBaseContextMethod = ContextWrapper::class.java.getDeclaredMethod("attachBaseContext", Context::class.java)
        attachBaseContextMethod.isAccessible = true

        attachBaseContextMethod.invoke(this, context)
    }
}
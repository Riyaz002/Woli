package com.wiseowl.woli.ui.notification

import com.wiseowl.woli.ui.notification.model.NotificationPayload

/**
 * Contract for handling notification
 *
 * @since version 22
 */
fun interface NotificationHandler {

    /**
     * Post notification to the system
     * @param notificationPayload notification data like `title`, `content`, `image`, etc.
     */
    fun showNotification(notificationPayload: NotificationPayload)
}
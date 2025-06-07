package com.wiseowl.woli.ui.notification.model

data class NotificationPayload(
    val id: Int,
    val title: String?,
    val content: String?,
    val image: String?
)
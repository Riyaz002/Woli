package com.wiseowl.woli.ui.notification

import com.wiseowl.woli.ui.notification.ParamKey.TITLE
import com.wiseowl.woli.ui.notification.ParamKey.IMAGE
import com.wiseowl.woli.ui.notification.ParamKey.SUBTITLE
import com.wiseowl.woli.domain.model.Notification


/**
 * Interface for handling notifications.
 */
fun interface NotificationHandler {

    /**
     * handles notification.
     * @param params - Object of [Notification] containing parameters like [TITLE], [SUBTITLE], [IMAGE], etc.
     *
     * @return true if notification is handled successfully, false otherwise.
     */
    fun handle(data: Notification): Boolean
}

enum class ParamKey(val key: String){
    ID("id"),
    TITLE("title"),
    SUBTITLE("content"),
    IMAGE("image")
}
package com.wiseowl.woli.data.service.notification

import com.wiseowl.woli.data.service.notification.ParamKey.TITLE
import com.wiseowl.woli.data.service.notification.ParamKey.IMAGE
import com.wiseowl.woli.data.service.notification.ParamKey.SUBTITLE


/**
 * Interface for handling notifications.
 */
fun interface NotificationHandler {

    /**
     * handles notification.
     * @param params notification parameters like [TITLE], [SUBTITLE], [IMAGE], etc.
     *
     * @return true if notification is handled successfully, false otherwise.
     */
    fun handle(params: Map<String, String>): Boolean
}

enum class ParamKey(val key: String){
    ID("id"),
    TITLE("title"),
    SUBTITLE("content"),
    IMAGE("image")
}
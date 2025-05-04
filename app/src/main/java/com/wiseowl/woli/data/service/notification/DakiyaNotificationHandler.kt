package com.wiseowl.woli.data.service.notification

import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.getOrNull
import com.riyaz.dakiya.core.model.Message
import com.wiseowl.woli.util.Logger.tryWithLog
import kotlin.random.Random

class DakiyaNotificationHandler: NotificationHandler {
    override fun handle(params: Map<String, String>): Boolean {
        return tryWithLog {
            val message = params.toDakiyaMessage()
            Dakiya.showNotification(message)
            return@tryWithLog true
        } == true
    }

    fun Map<String, String>.toDakiyaMessage(): Message{
        return Message(
            getOrDefault(ParamKey.ID.key, Random.nextInt().toString()).toInt(),
            getValue(ParamKey.TITLE.key).toString(),
            getOrNull(ParamKey.SUBTITLE.key),
            getOrNull(ParamKey.IMAGE.key)
        )
    }
}
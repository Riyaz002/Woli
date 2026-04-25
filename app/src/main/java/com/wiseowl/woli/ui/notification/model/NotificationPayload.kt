package com.wiseowl.woli.ui.notification.model

data class NotificationPayload(
    val id: Int,
    val title: String?,
    val content: String?,
    val image: String?,
    val channel: NotificationChannel
)

enum class NotificationChannel(val channelName: String){
    DEFAULT("default"),
    PROMOTIONAL("Promotional"),
    WALLPAPER_UPDATES("Wallpaper Updates"),
    DOWNLOADS("Wallpaper Downloads");

    companion object{
        fun String.toNotificationChannel(): NotificationChannel{
            return try{
                NotificationChannel.valueOf(this)
            } catch (e: IllegalArgumentException){
                DEFAULT
            }
        }
    }

}
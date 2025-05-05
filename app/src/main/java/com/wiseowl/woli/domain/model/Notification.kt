package com.wiseowl.woli.domain.model

data class Notification(
    val title: String,
    val subtitle: String? = null,
    val image: String,
    val progress: Progress
)

enum class Progress{
    DETERMINENT,
    INDETERMINENT
}

package com.wiseowl.woli.domain.repository.media.model

data class Media(
    val alt: String?,
    val avgColor: String?,
    val duration: Int?,
    val fullRes: Any?,
    val height: Int,
    val id: Long,
    val image: String?,
    val liked: Boolean?,
    val photographer: String?,
    val photographerId: Long?,
    val photographerUrl: String?,
    val src: Source?,
    val tags: List<Any?>?,
    val type: String?,
    val url: String,
    val width: Int
)
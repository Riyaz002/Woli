package com.wiseowl.woli.domain.repository.media.model

data class Photo(
    val alt: String,
    val avgColor: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographerId: Long,
    val photographerUrl: String,
    val src: ImageSource,
    val url: String,
    val width: Int
)
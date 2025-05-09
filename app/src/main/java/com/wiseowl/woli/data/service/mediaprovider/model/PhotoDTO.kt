package com.wiseowl.woli.data.service.mediaprovider.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoDTO(
    val alt: String,
    val avg_color: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Int,
    val photographer_url: String,
    val src: ImageSourceDTO,
    val url: String,
    val width: Int
)
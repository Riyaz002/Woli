package com.wiseowl.woli.data.service.mediaprovider.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageSourceDTO(
    val landscape: String,
    val large: String,
    val large2x: String,
    val medium: String,
    val original: String,
    val portrait: String,
    val small: String,
    val tiny: String
)
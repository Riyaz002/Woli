package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass
import com.wiseowl.woli.domain.repository.media.model.ImageSource

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
){
    fun toImageSource() = ImageSource(
        landscape = landscape,
        large = large,
        large2x = large2x,
        medium = medium,
        original = original,
        portrait = portrait,
        small = small,
        tiny = tiny
    )
}
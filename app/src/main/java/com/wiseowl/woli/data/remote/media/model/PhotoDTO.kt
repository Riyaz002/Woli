package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass
import com.wiseowl.woli.domain.repository.media.model.Photo

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
){
    fun toPhoto() = Photo(
        altImage = alt,
        avgColor = avg_color,
        height = height,
        id = id,
        liked = liked,
        photographer = photographer,
        photographerId = photographer_id,
        photographerUrl = photographer_url,
        src = src.toImageSource(),
        url = url,
        width = width
    )
}
package com.wiseowl.woli.domain.repository.media.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Collection(
    val description: String?,
    val id: String,
    val mediaCount: Int,
    val photosCount: Int,
    val isPrivate: Boolean,
    val title: String,
    val videosCount: Int,
    val images: List<Media>
)
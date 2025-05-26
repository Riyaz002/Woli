package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass
import com.wiseowl.woli.domain.repository.media.model.Collection

@JsonClass(generateAdapter = true)
data class CollectionPageItemDTO(
    val description: String?,
    val id: String,
    val media_count: Int,
    val photos_count: Int,
    val `private`: Boolean,
    val title: String,
    val videos_count: Int
){
    fun toCollection() = Collection(
        id = id,
        title = title,
        description = description,
        mediaCount = media_count,
        photosCount = photos_count,
        isPrivate = `private`,
        videosCount = videos_count,
        medias = listOf()
    )
}
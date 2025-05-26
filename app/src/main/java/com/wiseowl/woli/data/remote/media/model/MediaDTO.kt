package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass
import com.wiseowl.woli.domain.repository.media.model.Media
import com.wiseowl.woli.domain.repository.media.model.MediaType

@JsonClass(generateAdapter = true)
data class MediaDTO(
    val alt: String?,
    val avg_color: String?,
    val duration: Int?,
    val full_res: Any?,
    val height: Int,
    val id: Long,
    val image: String?,
    val liked: Boolean?,
    val photographer: String?,
    val photographer_id: Long?,
    val photographer_url: String?,
    val src: SourceDTO?,
    val tags: List<Any?>?,
    val type: String?,
    val url: String,
    val user: User?,
    val video_files: List<VideoFile>?,
    val video_pictures: List<VideoPicture>?,
    val width: Int
){
    fun toMedia() = Media(
        alt = alt,
        avgColor = avg_color,
        height = height,
        id = id,
        liked = liked,
        photographer = photographer,
        photographerId = photographer_id,
        photographerUrl = photographer_url,
        src = src?.toSource(),
        url = url,
        width = width,
        duration = duration,
        fullRes = full_res,
        image = image,
        tags = tags,
        type = type?.let { MediaType.valueOf(it) }
    )
}
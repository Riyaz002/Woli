package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoFile(
    val file_type: String,
    val height: Int?,
    val id: Int,
    val link: String,
    val quality: String,
    val width: Int?
)
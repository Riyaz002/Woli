package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoPicture(
    val id: Int,
    val nr: Int,
    val picture: String
)
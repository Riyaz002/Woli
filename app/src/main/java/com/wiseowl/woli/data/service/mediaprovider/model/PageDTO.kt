package com.wiseowl.woli.data.service.mediaprovider.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageDTO(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<PhotoDTO>,
    val total_results: Int
)
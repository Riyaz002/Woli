package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionDTO(
    val id: String,
    val media: List<MediaDTO>,
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val prev_page: String,
    val total_results: Int
)
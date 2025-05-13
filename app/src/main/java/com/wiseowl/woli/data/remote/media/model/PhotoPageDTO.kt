package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass
import com.wiseowl.woli.domain.repository.media.model.PhotoPage

@JsonClass(generateAdapter = true)
data class PhotoPageDTO(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<MediaDTO>,
    val total_results: Int
){
    fun toPage() = PhotoPage(
        nextPage = next_page,
        page = page,
        perPagePhoto = per_page,
        photos = photos.map { it.toMedia() },
        totalResults = total_results
    )
}
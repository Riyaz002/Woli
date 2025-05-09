package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass
import com.wiseowl.woli.domain.repository.media.model.Page

@JsonClass(generateAdapter = true)
data class PageDTO(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<PhotoDTO>,
    val total_results: Int
){
    fun toPage() = Page(
        nextPage = next_page,
        page = page,
        perPagePhoto = per_page,
        photos = photos.map { it.toPhoto() },
        totalResults = total_results
    )
}
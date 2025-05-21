package com.wiseowl.woli.data.remote.media.model

import com.squareup.moshi.JsonClass
import com.wiseowl.woli.domain.repository.media.model.CollectionPage

@JsonClass(generateAdapter = true)
data class CollectionPageDTO(
    val collections: List<CollectionPageItemDTO>,
    val next_page: String?,
    val page: Int,
    val per_page: Int,
    val prev_page: String?,
    val total_results: Int
){
    fun toCollectionPage() = CollectionPage(
        nextPage = next_page,
        previousPage = prev_page,
        page = page,
        perPagePhoto = per_page,
        collections = collections.map { it.toCollection() },
        totalResults = total_results
    )
}
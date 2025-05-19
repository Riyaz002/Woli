package com.wiseowl.woli.domain.repository.media.model

data class CollectionPage(
    val nextPage: String?,
    val previousPage: String?,
    val page: Int,
    val perPagePhoto: Int,
    val collections: List<Collection>,
    val totalResults: Int
)
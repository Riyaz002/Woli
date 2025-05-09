package com.wiseowl.woli.domain.repository.media.model

data class Page(
    val nextPage: String,
    val page: Int,
    val perPagePhoto: Int,
    val photos: List<Photo>,
    val totalResults: Int
)
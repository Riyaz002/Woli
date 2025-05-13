package com.wiseowl.woli.domain.repository.media.model

data class PhotoPage(
    val nextPage: String,
    val page: Int,
    val perPagePhoto: Int,
    val photos: List<Media>,
    val totalResults: Int
)
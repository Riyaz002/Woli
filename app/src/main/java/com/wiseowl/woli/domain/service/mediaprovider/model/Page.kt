package com.wiseowl.woli.domain.service.mediaprovider.model

data class Page(
    val nextPage: String,
    val page: Int,
    val perPagePhoto: Int,
    val photos: List<Photo>,
    val totalResults: Int
)
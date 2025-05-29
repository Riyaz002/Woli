package com.wiseowl.woli.ui.screen.collection.model

import com.wiseowl.woli.domain.repository.media.model.Media

data class CollectionModel(
    val category: String,
    val media: List<Media>? = null,
    val currentPage: Int,
    val hasNext: Boolean
)

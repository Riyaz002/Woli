package com.wiseowl.woli.ui.screen.categories.model

import com.wiseowl.woli.domain.repository.media.model.Collection


data class CollectionModel(
    val categories: List<Collection>?,
    val currentPage: Int,
    val hasNext: Boolean
)
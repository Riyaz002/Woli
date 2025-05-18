package com.wiseowl.woli.ui.screen.collection.model

import com.wiseowl.woli.domain.model.Image

data class CollectionModel(
    val category: String,
    val images: List<Image>? = null
)

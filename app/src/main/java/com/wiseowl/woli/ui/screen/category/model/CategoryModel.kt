package com.wiseowl.woli.ui.screen.category.model

import com.wiseowl.woli.domain.model.Image

data class CategoryModel(
    val category: String,
    val images: List<Image>? = null
)

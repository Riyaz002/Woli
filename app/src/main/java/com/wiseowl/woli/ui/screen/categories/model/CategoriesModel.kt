package com.wiseowl.woli.ui.screen.categories.model

import com.wiseowl.woli.domain.model.Category

data class CategoriesModel(
    val categories: List<Category>?,
    val currentPage: Int,
    val hasNext: Boolean
)
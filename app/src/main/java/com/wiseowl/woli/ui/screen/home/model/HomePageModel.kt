package com.wiseowl.woli.ui.screen.home.model

import com.wiseowl.woli.domain.model.Image

data class HomePageModel(
    val images: List<Image>?,
    val currentPage: Int = 1,
    val allPageLoaded: Boolean = false
)
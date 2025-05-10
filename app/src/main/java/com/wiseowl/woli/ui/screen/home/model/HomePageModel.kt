package com.wiseowl.woli.ui.screen.home.model

import com.wiseowl.woli.domain.repository.media.model.Photo

data class HomePageModel(
    val images: List<Photo>,
    val currentPage: Int
)
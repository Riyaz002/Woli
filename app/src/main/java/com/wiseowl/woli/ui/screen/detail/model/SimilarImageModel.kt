package com.wiseowl.woli.ui.screen.detail.model

import com.wiseowl.woli.domain.model.Image

data class SimilarImageModel(
    val images: List<Image>? = null,
    val shimmer: Boolean = true
)
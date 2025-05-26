package com.wiseowl.woli.ui.screen.detail.model

import com.wiseowl.woli.domain.repository.media.model.Media

data class DetailModel(
    val media: Media? = null,
    val description: String? = null,
    val categories: List<String> = listOf(),
    val accentColor: Int? = null,
    val complementaryColor: Int? = null,
    val imagePreviewPopupVisible: Boolean = false,
    val setWallpaperPopupVisible: Boolean = false,
    val similarImage: SimilarImageModel = SimilarImageModel()
)
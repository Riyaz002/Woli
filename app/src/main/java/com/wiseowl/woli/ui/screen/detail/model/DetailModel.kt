package com.wiseowl.woli.ui.screen.detail.model

import coil3.Bitmap
import com.wiseowl.woli.domain.model.Image

data class DetailModel(
    val image: Bitmap? = null,
    val description: String? = null,
    val categories: List<String> = listOf(),
    val accentColor: Int? = null,
    val complementaryColor: Int? = null,
    val imagePreviewPopupVisible: Boolean = false,
    val setWallpaperPopupVisible: Boolean = false,
    val similarImage: SimilarImageModel = SimilarImageModel()
)
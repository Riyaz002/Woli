package com.wiseowl.woli.ui.screen.detail.model

import coil3.Bitmap

data class DetailModel(
    val image: Bitmap? = null,
    val accentColor: Int? = null,
    val complementaryColor: Int? = null,
    val imagePreviewPopupVisible: Boolean = false,
    val setWallpaperPopupVisible: Boolean = false
)
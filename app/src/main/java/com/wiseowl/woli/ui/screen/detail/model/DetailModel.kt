package com.wiseowl.woli.ui.screen.detail.model

import com.wiseowl.woli.domain.model.Image

data class DetailModel(
    val image: Image? = null,
    val imagePreviewPopupVisible: Boolean = false
)
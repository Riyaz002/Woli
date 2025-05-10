package com.wiseowl.woli.ui.screen.home.model

import com.wiseowl.woli.domain.repository.media.model.Photo
import com.wiseowl.woli.ui.shared.model.FieldValue

data class HomePageModel(
    val search: FieldValue = FieldValue("Search", ""),
    val images: List<Photo>,
    val currentPage: Int
)
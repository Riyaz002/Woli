package com.wiseowl.woli.ui.screen.home.model

import com.wiseowl.woli.domain.repository.media.model.Media
import com.wiseowl.woli.ui.shared.model.FieldData

data class HomePageModel(
    val search: FieldData = FieldData("Search", ""),
    val images: List<Media>,
    val currentPage: Int
)
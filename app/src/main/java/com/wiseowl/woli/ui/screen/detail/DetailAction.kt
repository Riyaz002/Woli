package com.wiseowl.woli.ui.screen.detail

import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.domain.usecase.detail.SetWallpaperType


sealed class DetailAction: Action {
    data object OnClickImage: DetailAction()
    data object OnClickSetWallpaper: DetailAction()
    data object OnDismissImagePreview: DetailAction()
    data object OnDismissSetWallpaperDialog: DetailAction()
    data class OnClickSetAs(val setWallpaperType: SetWallpaperType): DetailAction()
    data class OnClickSimilarImage(val imageId: Int): DetailAction()
    data class OnClickCategory(val category: String): DetailAction()
}
package com.wiseowl.woli.ui.screen.detail

import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.usecase.detail.SetWallpaperType


sealed class DetailEvent: Action {
    data object OnClickImage: DetailEvent()
    data object OnClickSetWallpaper: DetailEvent()
    data object OnDismissImagePreview: DetailEvent()
    data object OnDismissSetWallpaperDialog: DetailEvent()
    data class OnClickSetAs(val setWallpaperType: SetWallpaperType): DetailEvent()
}
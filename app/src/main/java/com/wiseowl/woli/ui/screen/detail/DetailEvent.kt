package com.wiseowl.woli.ui.screen.detail

import com.wiseowl.woli.domain.event.Action


sealed class DetailEvent: Action {
    data object OnClickImage: DetailEvent()
    data object OnClickSetWallpaper: DetailEvent()
    data object OnDismissImagePreview: DetailEvent()
    data object OnDismissSetWallpaperDialog: DetailEvent()
    data object OnClickSetAsHomeScreen: DetailEvent()
    data object OnClickSetAsLockScreen: DetailEvent()
    data object OnClickSetAsBoth: DetailEvent()
    data object OnClickUseOtherApp: DetailEvent()
}
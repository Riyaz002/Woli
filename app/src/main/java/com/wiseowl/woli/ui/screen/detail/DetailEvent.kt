package com.wiseowl.woli.ui.screen.detail

import com.wiseowl.woli.domain.event.Event


sealed class DetailEvent: Event {
    data object OnClickImage: DetailEvent()
    data object OnClickSetWallpaper: DetailEvent()
    data object OnDismissImagePreview: DetailEvent()
}
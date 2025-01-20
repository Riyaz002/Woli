package com.wiseowl.woli.ui.screen.home

import com.wiseowl.woli.domain.event.Event

sealed class HomeEvent: Event {
    data class OnClickImage(val imageId: Int): HomeEvent()
    data class LoadNextPage(val page: Int): HomeEvent()
}
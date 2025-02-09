package com.wiseowl.woli.ui.screen.home

import com.wiseowl.woli.domain.event.Action

sealed class HomeEvent: Action {
    data class OnClickImage(val imageId: Int): HomeEvent()
    data class LoadNextPage(val page: Int): HomeEvent()
}
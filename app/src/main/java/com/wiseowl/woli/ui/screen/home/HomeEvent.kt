package com.wiseowl.woli.ui.screen.home

import com.wiseowl.woli.ui.event.Action

sealed class HomeEvent: Action {
    data class OnClickImage(val imageId: Long): HomeEvent()
    object LoadNextPage: HomeEvent()
    data class OnSearchChange(val query: String): HomeEvent()
    object OnClickSearch: HomeEvent()
}
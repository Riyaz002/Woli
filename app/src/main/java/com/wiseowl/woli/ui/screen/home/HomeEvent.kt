package com.wiseowl.woli.ui.screen.home

sealed class HomeEvent {
    data class OnClickImage(val imageId: Int): HomeEvent()
    data class LoadNextPage(val page: Int): HomeEvent()
}
package com.wiseowl.woli.ui.screen.home

import com.wiseowl.woli.ui.event.Action

sealed class HomeAction: Action {
    data class OnClickImage(val imageId: Long): HomeAction()
    object LoadNextPage: HomeAction()
    data class OnSearchChange(val query: String): HomeAction()
    object OnClickSearch: HomeAction()
    data class OnClickAddToFavourite(val mediaId: Long): HomeAction()
}
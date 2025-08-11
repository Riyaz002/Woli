package com.wiseowl.woli.ui.screen.favourites

import com.wiseowl.woli.ui.event.Action

sealed class FavouritesAction: Action {
    data class OnClickFavouritesIcon(val mediaId: Long): FavouritesAction()
    data class OnClickRemoveFromFavourites(val mediaId: Long): FavouritesAction()
    object OnClickDismissRemoveRequest: FavouritesAction()
}
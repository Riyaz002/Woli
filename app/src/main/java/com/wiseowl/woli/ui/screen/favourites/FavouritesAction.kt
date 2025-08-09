package com.wiseowl.woli.ui.screen.favourites

import com.wiseowl.woli.ui.event.Action

sealed class FavouritesAction: Action {
    data object OnLoginClick : FavouritesAction()
    data class OnEmailChange(val email: String) : FavouritesAction()
    data class OnPasswordChange(val password: String) : FavouritesAction()
}
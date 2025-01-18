package com.wiseowl.woli.ui.screen.home.model

sealed class HomeState {
    data object Loading: HomeState()
    data class Success(val homePageModel: HomePageModel): HomeState()
    data class Error(val message: String): HomeState()
}
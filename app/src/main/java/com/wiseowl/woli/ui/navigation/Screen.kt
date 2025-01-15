package com.wiseowl.woli.ui.navigation
sealed class Screen {
    data object HOME: Screen()
    data object DETAIL: Screen()
}
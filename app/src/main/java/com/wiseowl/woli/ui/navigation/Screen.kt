package com.wiseowl.woli.ui.navigation

import kotlinx.serialization.Serializable


sealed class Screen {
    @Serializable data object HOME: Screen()
    @Serializable data object DETAIL: Screen()
}
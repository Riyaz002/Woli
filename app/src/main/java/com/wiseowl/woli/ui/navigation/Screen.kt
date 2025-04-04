package com.wiseowl.woli.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    open val route: String = "$this"

    @Serializable data object HOME: Screen()
    @Serializable data object DETAIL: Screen(){
        const val ARG_IMAGE_ID = "imageId"
        override var route: String = super.route + "?$ARG_IMAGE_ID={$ARG_IMAGE_ID}"
    }
    @Serializable data object CATEGORY: Screen(){
        const val ARG_CATEGORY = "category"
        override var route: String = super.route + "?$ARG_CATEGORY={$ARG_CATEGORY}"
    }
    @Serializable data object REGISTRATION: Screen()
    @Serializable data object LOGIN: Screen()
    @Serializable data object CATEGORIES: Screen()
    @Serializable data object Profile: Screen()
}
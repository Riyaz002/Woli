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

    companion object {
        fun fromRoute(route: String): Screen? {
            val baseRoute = route.substringBefore("?")
            return when (baseRoute) {
                HOME.route -> HOME
                DETAIL.route.substringBefore("?") -> DETAIL
                CATEGORY.route.substringBefore("?") -> CATEGORY
                REGISTRATION.route -> REGISTRATION
                LOGIN.route -> LOGIN
                CATEGORIES.route -> CATEGORIES
                Profile.route -> Profile
                else -> null
            }
        }
    }
}
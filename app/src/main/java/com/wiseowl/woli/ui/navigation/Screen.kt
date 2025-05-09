package com.wiseowl.woli.ui.navigation

import android.util.Log
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    open val route: String = "$this"
    val routeLowerCase: String = "$this".lowercase()

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
    @Serializable data object PrivacyPolicy: Screen()

    companion object {
        fun fromRoute(route: String): Screen? {
            val baseRoute = route.substringBefore("?").lowercase()
            return when (baseRoute) {
                HOME.routeLowerCase -> HOME
                DETAIL.routeLowerCase.substringBefore("?") -> DETAIL
                CATEGORY.routeLowerCase.substringBefore("?") -> CATEGORY
                REGISTRATION.routeLowerCase -> REGISTRATION
                LOGIN.routeLowerCase -> LOGIN
                CATEGORIES.routeLowerCase -> CATEGORIES
                Profile.routeLowerCase -> Profile
                else -> null
            }
        }
    }
}
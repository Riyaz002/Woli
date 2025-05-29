package com.wiseowl.woli.ui.navigation

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
    @Serializable data object COLLECTION: Screen(){
        const val ARG_COLLECTION_ID = "collectionId"
        const val ARG_COLLECTION_TITLE = "collectionTitle"
        override var route: String = super.route + "?$ARG_COLLECTION_ID={$ARG_COLLECTION_ID}&$ARG_COLLECTION_TITLE={$ARG_COLLECTION_TITLE}"
    }
    @Serializable data object REGISTRATION: Screen()
    @Serializable data object LOGIN: Screen()
    @Serializable data object COLLECTIONS: Screen()
    @Serializable data object Profile: Screen()
    @Serializable data object PrivacyPolicy: Screen()

    companion object {
        fun fromRoute(route: String): Screen? {
            val baseRoute = route.substringBefore("?").lowercase()
            return when (baseRoute) {
                HOME.routeLowerCase -> HOME
                DETAIL.routeLowerCase.substringBefore("?") -> DETAIL
                COLLECTION.routeLowerCase.substringBefore("?") -> COLLECTION
                REGISTRATION.routeLowerCase -> REGISTRATION
                LOGIN.routeLowerCase -> LOGIN
                COLLECTIONS.routeLowerCase -> COLLECTIONS
                Profile.routeLowerCase -> Profile
                else -> null
            }
        }
    }
}
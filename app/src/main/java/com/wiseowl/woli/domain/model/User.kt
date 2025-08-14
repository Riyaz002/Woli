package com.wiseowl.woli.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val firstName: String,
    val lastName: String,
    val uid: String,
    val email: String,
    val favourites: List<Long>?
){
    // Overloading the '+' operator
    operator fun plus(other: User): User {
        val mergedFavourites = mutableListOf<Long>()
        mergedFavourites.addAll(favourites.orEmpty())
        mergedFavourites.addAll(other.favourites.orEmpty())
        return copy(favourites = mergedFavourites)
    }
}


var GuestUser = User(
    firstName = "Guest",
    lastName = "User",
    uid = "1",
    email = "guestemail",
    favourites = emptyList()
)
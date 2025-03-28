package com.wiseowl.woli.domain.model

data class User(
    val firstName: String,
    val lastName: String,
    val uid: String,
    val email: String,
    val favourites: List<String>?
)

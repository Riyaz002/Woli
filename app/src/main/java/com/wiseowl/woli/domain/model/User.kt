package com.wiseowl.woli.domain.model

import android.provider.ContactsContract.CommonDataKinds.Email

data class User(
    val firstName: String,
    val lastName: String,
    val uid: String,
    val email: String,
    val favourites: List<String>?
)

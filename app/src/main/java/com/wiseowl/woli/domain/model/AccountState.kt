package com.wiseowl.woli.domain.model

data class AccountState(
    val isLoggedIn: Boolean,
    val currentUser: User?
)
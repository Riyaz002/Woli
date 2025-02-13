package com.wiseowl.woli.domain.model


data class Error(
    val reason: String,
    val retry: (() -> Unit)? = null
)

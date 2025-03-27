package com.wiseowl.woli.domain.model

data class Page<T>(
    val page: Int,
    val data: List<T>?
)
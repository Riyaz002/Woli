package com.wiseowl.woli.domain.model

data class Image(
    val id: Int,
    val url: String,
    val description: String,
    val categories: List<String>,
    val color: Color?
)
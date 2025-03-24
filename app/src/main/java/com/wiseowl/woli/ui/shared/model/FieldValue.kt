package com.wiseowl.woli.ui.shared.model

data class FieldValue(
    val label: String,
    val value: String = "",
    val error: String? = null,
    val valid: Boolean = error == null && value.isNotEmpty()
)

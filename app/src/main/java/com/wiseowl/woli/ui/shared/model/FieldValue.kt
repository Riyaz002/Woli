package com.wiseowl.woli.ui.shared.model

data class FieldValue(
    val label: String,
    val value: String = "",
    var error: String? = null,
    val valid: Boolean = error == null && value.isNotEmpty()
){
    fun copy(
        value: String = this.value,
        error: String? = this.error
    ) = FieldValue(label = label, value = value, error = error)
}

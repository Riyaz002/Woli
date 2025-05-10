package com.wiseowl.woli.ui.shared.model

data class FieldData(
    val label: String,
    val value: String = "",
    var error: String? = null,
    val valid: Boolean = error == null && value.isNotEmpty()
){
    fun copy(
        value: String = this.value,
        error: String? = this.error
    ) = FieldData(label = label, value = value, error = error)
}

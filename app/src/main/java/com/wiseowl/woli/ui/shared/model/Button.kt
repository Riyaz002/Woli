package com.wiseowl.woli.ui.shared.model

import com.wiseowl.woli.domain.event.Action

data class Button(
    val text: String,
    val action: Action,
    val enabled: Boolean = true
)

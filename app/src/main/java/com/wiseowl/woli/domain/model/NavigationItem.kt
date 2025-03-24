package com.wiseowl.woli.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.wiseowl.woli.domain.event.Action

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val action: Action,
    val selected: Boolean = false
)

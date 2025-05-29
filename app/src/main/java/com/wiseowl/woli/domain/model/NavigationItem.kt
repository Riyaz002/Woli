package com.wiseowl.woli.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.wiseowl.woli.ui.navigation.Screen

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

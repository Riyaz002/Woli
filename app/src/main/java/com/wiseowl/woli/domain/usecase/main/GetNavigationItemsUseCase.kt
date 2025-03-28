package com.wiseowl.woli.domain.usecase.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.model.NavigationItem
import com.wiseowl.woli.ui.navigation.Screen

class GetNavigationItemsUseCase {
    operator fun invoke(): List<NavigationItem>{
        return listOf(
            NavigationItem("Home", Icons.Default.Home, Action.Navigate(Screen.HOME), true),
            NavigationItem("Categories", Icons.Default.Menu, Action.Navigate(Screen.CATEGORY))
        )
    }
}
package com.wiseowl.woli.domain.usecase.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import com.wiseowl.woli.domain.model.NavigationItem
import com.wiseowl.woli.ui.navigation.Screen

class GetNavigationItemsUseCase {
    operator fun invoke(selectedScreen: Screen?): NavBarData{
        return NavBarData(selectedScreen = selectedScreen)
    }
}

class NavBarData(
    val navItems: List<NavigationItem> = listOf(
        NavigationItem("Home", Icons.Default.Home, Screen.HOME),
        NavigationItem("Categories", Icons.Default.Menu, Screen.CATEGORIES),
        NavigationItem("Profile", Icons.Default.Person, Screen.Profile)
    ),
    val selectedScreen: Screen?
)
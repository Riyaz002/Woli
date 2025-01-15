package com.wiseowl.woli.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wiseowl.woli.ui.screen.detail.Detail
import com.wiseowl.woli.ui.screen.home.Home

@Composable
fun Root(
    modifier: Modifier,
    navController: NavHostController,
    startScreen: Screen = Screen.HOME
) {
    NavHost(modifier = modifier, navController = navController, startDestination = startScreen) {
        composable<Screen.HOME> { Home() }
        composable<Screen.DETAIL> { Detail() }
    }
}
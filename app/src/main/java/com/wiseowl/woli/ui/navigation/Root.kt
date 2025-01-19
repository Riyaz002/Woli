package com.wiseowl.woli.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wiseowl.woli.ui.screen.detail.Detail
import com.wiseowl.woli.ui.screen.home.Home

@Composable
fun Root(
    modifier: Modifier,
    navController: NavHostController,
    startScreen: String = Screen.HOME.route
) {
    NavHost(modifier = modifier, navController = navController, startDestination = startScreen) {
        composable(route = Screen.HOME.route) { Home() }
        composable(
            route = Screen.DETAIL.route,
            arguments = listOf(
                navArgument(Screen.DETAIL.ARG_IMAGE_ID) { type = NavType.StringType }
            )
        ) { Detail(imageId = it.arguments!!.getString(Screen.DETAIL.ARG_IMAGE_ID)!!) }
    }
}
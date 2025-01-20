package com.wiseowl.woli.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    innerPaddingValues: PaddingValues,
    navController: NavHostController,
    startScreen: String = Screen.HOME.route
) {
    NavHost(modifier = modifier, navController = navController, startDestination = startScreen) {
        composable(route = Screen.HOME.route) { Home(modifier.padding(innerPaddingValues)) }
        composable(
            route = Screen.DETAIL.route,
            arguments = listOf(
                navArgument(Screen.DETAIL.ARG_IMAGE_ID) { type = NavType.StringType }
            )
        ) { Detail(modifier, imageId = it.arguments!!.getString(Screen.DETAIL.ARG_IMAGE_ID)!!) }
    }
}
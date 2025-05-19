package com.wiseowl.woli.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wiseowl.woli.ui.screen.collections.Categories
import com.wiseowl.woli.ui.screen.collection.Category
import com.wiseowl.woli.ui.screen.detail.Detail
import com.wiseowl.woli.ui.screen.home.Home
import com.wiseowl.woli.ui.screen.login.Login
import com.wiseowl.woli.ui.screen.privacypolicy.PrivacyPolicy
import com.wiseowl.woli.ui.screen.profile.Profile
import com.wiseowl.woli.ui.screen.registration.Registration

@Composable
fun Root(
    modifier: Modifier,
    navController: NavHostController,
    startScreen: String
) {
    NavHost(modifier = modifier, navController = navController, startDestination = startScreen) {
        composable(route = Screen.HOME.route) { Home(modifier) }
        composable(
            route = Screen.DETAIL.route,
            arguments = listOf(
                navArgument(Screen.DETAIL.ARG_IMAGE_ID) { type = NavType.StringType }
            )
        ) { Detail(modifier, imageId = it.arguments!!.getString(Screen.DETAIL.ARG_IMAGE_ID)!!) }
        composable(
            route = Screen.CATEGORY.route,
            arguments = listOf(
                navArgument(Screen.CATEGORY.ARG_CATEGORY) { type = NavType.StringType }
            )
        ) { Category(modifier, category = it.arguments!!.getString(Screen.CATEGORY.ARG_CATEGORY)!!) }
        composable(route = Screen.REGISTRATION.route) { Registration(modifier) }
        composable(route = Screen.LOGIN.route) { Login(modifier) }
        composable(route = Screen.CATEGORIES.route) { Categories(modifier) }
        composable(route = Screen.Profile.route) { Profile(modifier) }
        composable(route = Screen.PrivacyPolicy.route) { PrivacyPolicy(modifier) }
    }
}
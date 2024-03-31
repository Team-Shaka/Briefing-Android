package store.newsbriefing.app.feature.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val homeRoute = "home_route"

fun NavController.navigateToHome() {
    navigate(homeRoute)
}

fun NavGraphBuilder.homeScreen() {
    composable(
        route = homeRoute
    ) {
        HomeRoute()
    }
}
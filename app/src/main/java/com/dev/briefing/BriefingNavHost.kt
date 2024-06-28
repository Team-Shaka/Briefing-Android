package com.dev.briefing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import store.newsbriefing.app.feature.auth.navigateToSignIn
import store.newsbriefing.app.feature.auth.signInRoute
import store.newsbriefing.app.feature.auth.signInScreen
import store.newsbriefing.app.feature.bookmark.bookmarkScreen
import store.newsbriefing.app.feature.bookmark.navigateToBookmark
import store.newsbriefing.app.feature.home.homeRoute
import store.newsbriefing.app.feature.home.homeScreen
import store.newsbriefing.app.feature.newsdetail.navigateToNewsDetail
import store.newsbriefing.app.feature.newsdetail.newsDetailScreen
import store.newsbriefing.app.feature.setting.navigateToSetting
import store.newsbriefing.app.feature.setting.settingScreen

@Composable
fun BriefingNavHost(
    modifier: Modifier = Modifier,
    appState: BriefingAppState
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = signInRoute
    ) {
        homeScreen(
            showSnackbar = appState::showSnackBar,
            navigateToSettingRoute = appState.navController::navigateToSetting,
            navigateToBookmarkRoute = appState.navController::navigateToBookmark,
            navigateToNewsDetail = appState.navController::navigateToNewsDetail
        )
        bookmarkScreen(
            showSnackbar = appState::showSnackBar,
            navigateUp = appState.navController::navigateUp,
            navigateToNewsDetail = appState.navController::navigateToNewsDetail
        )
        newsDetailScreen(
            showSnackbar = appState::showSnackBar,
            navigateUp = appState.navController::navigateUp
        )
        settingScreen(
            showSnackbar = appState::showSnackBar,
            navigateUp = appState.navController::navigateUp,
            navigateToSignIn = appState.navController::navigateToSignIn,
            appVersion = BuildConfig.VERSION_NAME
        )
        signInScreen(
            showSnackBar = appState::showSnackBar,
            navigateToHome = {
                appState.navController.navigate(
                    route = homeRoute,
                    builder = {
                        popUpTo(signInRoute) { inclusive = true }
                    }
                )
            }
        )
    }
}
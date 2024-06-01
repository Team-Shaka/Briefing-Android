package com.dev.briefing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import store.newsbriefing.app.feature.auth.signInRoute
import store.newsbriefing.app.feature.auth.signInScreen
import store.newsbriefing.app.feature.bookmark.bookmarkScreen
import store.newsbriefing.app.feature.home.homeRoute
import store.newsbriefing.app.feature.home.homeScreen
import store.newsbriefing.app.feature.newsdetail.newsDetailScreen
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
            showSnackbar = appState::showSnackBar
        )
        bookmarkScreen(
            showSnackbar = appState::showSnackBar
        )
        newsDetailScreen(
            showSnackbar = appState::showSnackBar
        )
        settingScreen(
            showSnackbar = appState::showSnackBar
        )
        signInScreen()
    }
}
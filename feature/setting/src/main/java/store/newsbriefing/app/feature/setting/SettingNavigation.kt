package store.newsbriefing.app.feature.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val settingRoute = "setting_route"

fun NavController.navigateToSetting() {
    navigate(settingRoute)
}

fun NavGraphBuilder.settingScreen(
    showSnackbar: (String) -> Unit
) {
    composable(
        route = settingRoute
    ) {
        SettingRoute(
            showSnackbar = showSnackbar
        )
    }
}
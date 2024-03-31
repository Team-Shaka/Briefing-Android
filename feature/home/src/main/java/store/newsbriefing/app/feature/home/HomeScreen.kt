package store.newsbriefing.app.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController

@Composable
internal fun HomeRoute(
    showSnackbar: (String) -> Unit
) {
    HomeScreen(
        showSnackbar = showSnackbar
    )
}

@Composable
internal fun HomeScreen(
    showSnackbar: (String) -> Unit
) {

}

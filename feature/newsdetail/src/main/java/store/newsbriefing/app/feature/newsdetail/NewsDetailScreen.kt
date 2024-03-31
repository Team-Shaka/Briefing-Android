package store.newsbriefing.app.feature.newsdetail

import androidx.compose.runtime.Composable

@Composable
internal fun NewsDetailRoute(
    showSnackbar: (String) -> Unit
) {
    NewsDetailScreen(
        showSnackbar = showSnackbar
    )
}

@Composable
internal fun NewsDetailScreen(
    showSnackbar: (String) -> Unit
) {

}
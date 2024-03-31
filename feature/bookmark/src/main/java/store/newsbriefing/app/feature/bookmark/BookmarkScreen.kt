package store.newsbriefing.app.feature.bookmark

import androidx.compose.runtime.Composable

@Composable
internal fun BookmarkRoute(
    showSnackbar: (String) -> Unit
) {
    BookmarkScreen(
        showSnackbar = showSnackbar
    )
}

@Composable
internal fun BookmarkScreen(
    showSnackbar: (String) -> Unit
) {

}
package store.newsbriefing.app.feature.bookmark

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val bookmarkRoute = "bookmark_route"

fun NavController.navigateToBookmark() {
    navigate(bookmarkRoute)
}

fun NavGraphBuilder.bookmarkScreen() {
    composable(
        route = bookmarkRoute
    ) {
        BookmarkRoute()
    }
}
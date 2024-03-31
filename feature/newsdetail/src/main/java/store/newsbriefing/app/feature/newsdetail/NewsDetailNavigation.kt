package store.newsbriefing.app.feature.newsdetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val NewsDetailRoute = "news_detail_route"

fun NavController.navigateToNewsDetail() {
    navigate(NewsDetailRoute)
}

fun NavGraphBuilder.newsDetailScreen() {
    composable(
        route = NewsDetailRoute
    ) {
        NewsDetailRoute()
    }
}
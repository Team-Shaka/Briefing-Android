package store.newsbriefing.app.feature.newsdetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

internal const val NEWS_ID_ARG = "newsId"
const val NewsDetailRoute = "news_detail_route"

internal class NewsDetailArgs(val newsId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(URLDecoder.decode(checkNotNull(savedStateHandle[NEWS_ID_ARG]), URL_CHARACTER_ENCODING))
}

fun NavController.navigateToNewsDetail(newsId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(createNewsDetailRoute(newsId)) {
        navOptions()
    }
}

fun createNewsDetailRoute(newsId: String): String {
    val encodedId = URLEncoder.encode(newsId, URL_CHARACTER_ENCODING)
    return "$NewsDetailRoute/$encodedId"
}

fun NavGraphBuilder.newsDetailScreen(
    showSnackbar: (String) -> Unit,
    navigateUp: () -> Unit
) {
    composable(
        route = "${NewsDetailRoute}/{$NEWS_ID_ARG}",
        arguments = listOf(
            navArgument(NEWS_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        NewsDetailRoute(
            showSnackbar = showSnackbar,
            navigateUp = navigateUp
        )
    }
}
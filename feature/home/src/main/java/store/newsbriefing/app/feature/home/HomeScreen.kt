package store.newsbriefing.app.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import store.newsbriefing.app.core.common.util.toBriefingDate
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.core.designsystem.theme.ProductSans
import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.BriefingArticleSummary
import java.util.Date

@Composable
internal fun HomeRoute(
    showSnackbar: (String) -> Unit,
    navigateToBookmarkRoute: () -> Unit,
    navigateToSettingRoute: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    )

    LaunchedEffect(Unit) {
        homeViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeEvent.ErrorOccurred -> {
                    showSnackbar(event.message)
                }
            }
        }
    }

    HomeScreen(
        uiState = uiState,
        showSnackbar = showSnackbar,
        loadBriefings = homeViewModel::loadBriefings,
        navigateToBookmarkRoute = navigateToBookmarkRoute,
        navigateToSettingRoute = navigateToSettingRoute
    )
}

@Composable
@Preview
internal fun ArticleItemPreview(){
    BriefingTheme {
        ArticleItem(
            rank = 1,
            title = "배터리 혁명",
            subtitle = "2차 전지 혁명으로 인한 놀라운 발견과 문제 해결",
            scrapCount = 1000,
            onClick = {}
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    uiState: HomeUiState,
    loadBriefings: (BriefingArticleCategory, Boolean) -> Unit,
    showSnackbar: (String) -> Unit = { },
    navigateToBookmarkRoute: () -> Unit = { },
    navigateToSettingRoute: () -> Unit = { }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BriefingTheme.colorScheme.BackgroundWhite),
    ) {
        HomeHeader(
            navigateToBookmarkRoute = navigateToBookmarkRoute,
            navigateToSettingRoute = navigateToSettingRoute
        )

        val categories = HomeCategory.entries

        val pagerState = rememberPagerState(pageCount = { categories.size })
        var selectedTabIndex by remember { mutableIntStateOf(0) }

        val scope = rememberCoroutineScope()

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                selectedTabIndex = page
                loadBriefings(categories[page].category, false)
            }
        }

        ScrollableTabRow(
            items = categories.map { stringResource(it.title) },
            selectedItemIndex = selectedTabIndex
        ) {
            scope.launch { pagerState.animateScrollToPage(it) }
        }

        val isRefreshing = uiState.articles[categories[pagerState.currentPage].category]?.let {
            when (it) {
                is BriefingCategoryArticleUiState.Loading -> true
                else -> false
            }
        } ?: false

        HorizontalPager(state = pagerState) { page ->
            HomeScreenArticleListSection(
                isRefreshing = isRefreshing,
                onRefresh = {
                    loadBriefings(categories[page].category, true)
                },
                updatedAt = uiState.articles[categories[page].category]?.let {
                    when (it) {
                        is BriefingCategoryArticleUiState.Success -> it.categoryArticles.createdAt
                        else -> null
                    }
                },
                articles = uiState.articles[categories[page].category]?.let {
                    when (it) {
                        is BriefingCategoryArticleUiState.Success -> it.categoryArticles.briefings
                        else -> emptyList()
                    }
                } ?: emptyList()
            ) {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenArticleListSection(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    updatedAt: Date?,
    articles: List<BriefingArticleSummary>,
    onArticleClick: (Int) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val scaleFraction = if (pullToRefreshState.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState.progress).coerceIn(0f, 1f)

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing && !isRefreshing) {
            onRefresh()
        }
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing && pullToRefreshState.isRefreshing) {
            pullToRefreshState.endRefresh()
        }
        if (isRefreshing && !pullToRefreshState.isRefreshing) {
            pullToRefreshState.startRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection),
        contentAlignment = Alignment.TopCenter
    ) {
        PullToRefreshContainer(
            modifier = Modifier.graphicsLayer(
                scaleX = scaleFraction,
                scaleY = scaleFraction
            ),
            state = pullToRefreshState
        )

        ArticleListSection(
            articles = articles,
            updatedAt = updatedAt,
            onClick = onArticleClick
        ) {
            onRefresh()
        }
    }
}

@Composable
private fun HomeHeader(
    navigateToBookmarkRoute: () -> Unit,
    navigateToSettingRoute: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 28.dp, top = 28.dp, bottom = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.home_title),
            style = BriefingTheme.typography.SubtitleStyleBold,
            color = BriefingTheme.colorScheme.PrimaryBlue
        )

        Row(
            modifier = Modifier.align(Alignment.Bottom),
            horizontalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            ActionBarButton(iconDrawableRes = R.drawable.ic_action_bar_bookmark,) {
                navigateToBookmarkRoute()
            }

            ActionBarButton(iconDrawableRes = R.drawable.ic_action_bar_setting) {
                navigateToSettingRoute()
            }
        }
    }
}

@Composable
private fun ActionBarButton(
    @DrawableRes iconDrawableRes : Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Icon(
            painter = painterResource(id = iconDrawableRes),
            tint = Color.Unspecified,
            contentDescription = null
        )
    }
}

@Composable
private fun ArticleListDate(
    createdAt: String,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 8.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = createdAt,
            style = BriefingTheme.typography.ContextStyleRegular,
            color = BriefingTheme.colorScheme.TextGray
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = BriefingTheme.colorScheme.TextGray,

                    )
                .size(27.dp)
                .clip(CircleShape)
                .clickable { onRefresh() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = null,
                tint = BriefingTheme.colorScheme.TextGray,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun ArticleListSection(
    articles: List<BriefingArticleSummary>,
    updatedAt: Date?,
    onClick: (Int) -> Unit,
    onRefresh: () -> Unit
) {
    LazyColumn {
        item {
            ArticleListDate(updatedAt?.toBriefingDate() ?: "") {
                onRefresh()
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BriefingTheme.colorScheme.SeperatorGray)
            )
        }
        items(articles) { article ->
            ArticleItem(
                rank = article.ranks,
                title = article.title,
                subtitle = article.subtitle,
                scrapCount = article.scrapCount,
                onClick = { onClick(article.id) }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BriefingTheme.colorScheme.SeperatorGray)
            )
        }
    }
}

@Composable
private fun ArticleItem(
    rank: Int,
    title: String,
    subtitle: String,
    scrapCount: Int,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                top = 15.dp,
                bottom = 7.dp,
                start = 14.dp,
                end = 24.dp,
            )
    ) {
        Text(
            modifier = Modifier
                .width(54.dp)
                .padding(top = 2.dp),
            text = "${rank}.",
            style = TextStyle(
                fontSize = 35.sp,
                fontFamily = ProductSans,
                fontWeight = FontWeight(700),
                color = BriefingTheme.colorScheme.PrimaryBlue,
                textAlign = TextAlign.Right,
            )
        )

        Spacer(Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                style = BriefingTheme.typography.SubtitleStyleBold
            )
            Text(
                text = subtitle,
                style = BriefingTheme.typography.ContextStyleRegular25,
                color = BriefingTheme.colorScheme.TextGray,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.align(Alignment.BottomEnd), verticalAlignment =
                    Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(17.dp),
                        painter = painterResource(id = R.drawable.ic_bookmark),
                        contentDescription = null,
                        tint = BriefingTheme.colorScheme.TextGray
                    )
                    Text(
                        text = "$scrapCount",
                        style = BriefingTheme.typography.DetailStyleRegular.copy(color = BriefingTheme.colorScheme.TextGray)
                    )
                }
            }
        }
    }
}
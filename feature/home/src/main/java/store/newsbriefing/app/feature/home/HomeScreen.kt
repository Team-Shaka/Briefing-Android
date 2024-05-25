package store.newsbriefing.app.feature.home

import android.graphics.Paint.Align
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import store.newsbriefing.app.core.common.util.toBriefingDate
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.core.designsystem.theme.ProductSans
import store.newsbriefing.app.core.model.BriefingArticle
import store.newsbriefing.app.core.model.BriefingArticleSummary
import java.time.LocalDateTime
import java.util.Date

@Composable
internal fun HomeRoute(
    showSnackbar: (String) -> Unit,
    navigateToBookmarkRoute: () -> Unit,
    navigateToSettingRoute: () -> Unit
) {
    HomeScreen(
        showSnackbar = showSnackbar,
        navigateToBookmarkRoute = navigateToBookmarkRoute,
        navigateToSettingRoute = navigateToSettingRoute
    )

}

@Composable
@Preview
internal fun HomeScreenPreview(){
    BriefingTheme {
        HomeScreen()
    }
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

val categories = HomeCategory.entries
val articles = listOf(
    BriefingArticleSummary(
        id = 1,
        ranks = 1,
        title = "배터리 혁명",
        subtitle = "2차 전지 혁명으로 인한 놀라운 발견과 문제 해결",
        scrapCount = 1000
    ),
    BriefingArticleSummary(
        id = 2,
        ranks = 2,
        title = "럼피스킨병 확진",
        subtitle = "영남 지역에서 첫 럼피스킨병 확진자가 발생하였다.",
        scrapCount = 1000
    ),
    BriefingArticleSummary(
        id = 3,
        ranks = 3,
        title = "이스라엘 가자지구",
        subtitle = "이스라엘 군이 가자지구 내 군사작전을 계속하면서 우려가 계속되고 있다.",
        scrapCount = 1000
    ),
    BriefingArticleSummary(
        id = 4,
        ranks = 4,
        title = "국힘 혁신위",
        subtitle = "'대사면'건의에 대한 반발이 일어나고 있다.",
        scrapCount = 1000
    ),
    BriefingArticleSummary(
        id = 5,
        ranks = 5,
        title = "리커창 추모",
        subtitle = "리커창에 대한 추모의 열기가 계속되고 있다.",
        scrapCount = 1000
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    showSnackbar: (String) -> Unit = { },
    navigateToBookmarkRoute: () -> Unit = { },
    navigateToSettingRoute: () -> Unit = { }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BriefingTheme.colorScheme.BackgroundWhite),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 28.dp, top = 28.dp, bottom = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Briefing",
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

        val pagerState = rememberPagerState(pageCount = { categories.size })
        val scope = rememberCoroutineScope()

        var selectedItemIndex by remember { mutableStateOf(0) }

        ScrollableTabRow(
            items = categories.map { it.title },
            selectedItemIndex = selectedItemIndex
        ) {
            selectedItemIndex = it
        }

        HorizontalPager(state = pagerState) { page ->
            ArticleListSection(
                articles = articles,
                onClick = { },
                onRefresh = {}
            )
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
    onClick: (Int) -> Unit,
    onRefresh: () -> Unit
) {
    LazyColumn {
        item {
            ArticleListDate(Date().toBriefingDate()) {
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
package store.newsbriefing.app.feature.newsdetail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import store.newsbriefing.app.core.designsystem.LoadingDialog
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.core.designsystem.theme.Pretendard
import store.newsbriefing.app.core.model.BriefingArticleCategory
import store.newsbriefing.app.core.model.BriefingArticleRelated
import store.newsbriefing.app.core.model.TimeOfDay

@Composable
internal fun NewsDetailRoute(
    showSnackbar: (String) -> Unit,
    navigateUp: () -> Unit,
    newsDetailViewModel: NewsDetailViewModel = hiltViewModel()
) {
    val uiState by newsDetailViewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    )

    LaunchedEffect(Unit) {
        newsDetailViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NewsDetailEvent.ErrorOccurred -> {
                    showSnackbar(event.message)
                }
            }
        }
    }

    NewsDetailScreen(
        uiState = uiState,
        setScrap = newsDetailViewModel::setScrap,
        unScrap = newsDetailViewModel::unScrap,
        showSnackbar = showSnackbar,
        navigateUp = navigateUp
    )
}

@Composable
internal fun NewsDetailScreen(
    uiState: NewsDetailUiState,
    setScrap: (Long) -> Unit,
    unScrap: (Long) -> Unit,
    showSnackbar: (String) -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    when (uiState.article) {
        is BriefingArticleUiState.Loading -> {
            LoadingDialog()
        }
        is BriefingArticleUiState.Error -> {
            // Error
        }
        is BriefingArticleUiState.Success -> {
            val news = uiState.article.data

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BriefingTheme.colorScheme.BackgroundWhite)
                    .verticalScroll(rememberScrollState())
            ) {
                TopBar(
                    onBack = navigateUp,
                ) {
                    // 공유하기 기능
                }

                NewsDetailHeader(
                    title = news.title,
                    category = news.category,
                    ranks = news.ranks,
                    date = news.date,
                    timeOfDay = news.timeOfDay,
                    gptModel = news.gptModel
                )

                NewsSummarySection(
                    title = news.title,
                    content = news.content,
                )

                Spacer(modifier = Modifier.height(65.dp))

                ScrapButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    isBookmarked = news.isScrap
                ) {
                    if (news.isScrap) {
                        unScrap(news.id)
                    } else {
                        setScrap(news.id)
                    }
                }

                Spacer(modifier = Modifier.height(43.dp))

                RelatedNewsSection(
                    relatedNewsList = news.articles
                ) { url ->
                    val webPage: Uri = Uri.parse(url)
                    val intent = Intent(Intent.ACTION_VIEW, webPage)
                    startActivity(context, intent, null)
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    onBack: () -> Unit,
    onShare: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 21.dp,
                vertical = 7.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                modifier = Modifier.size(33.dp),
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = null
            )
        }

        IconButton(onClick = onShare) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun NewsSummarySection(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    Column(
        modifier = modifier.padding(horizontal = 21.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 28.sp
            ),
            color = BriefingTheme.colorScheme.TextBlack
        )

        Text(
            text = content,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            color = BriefingTheme.colorScheme.TextBlack
        )
    }
}

@Composable
private fun NewsDetailHeader(
    modifier: Modifier = Modifier,
    title: String,
    category: BriefingArticleCategory,
    ranks: Int,
    date: String,
    timeOfDay: TimeOfDay,
    gptModel: String
) {
    Column(
        modifier = modifier
            .padding(horizontal = 21.dp)
    ) {
        Spacer(modifier = Modifier.height(13.dp))

        NewsCategoryLabel(
            category = category,
            ranks = ranks
        )

        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = title,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
                lineHeight = 25.sp
            )
        )

        Spacer(modifier = Modifier.height(7.dp))

        NewsDate(
            date = "$date ${stringResource(id = timeOfDay.getStringResId())}",
            gptModel = gptModel
        )

        Spacer(modifier = Modifier.height(11.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BriefingTheme.colorScheme.SeperatorGray)
        )

        Spacer(modifier = Modifier.height(21.dp))
    }
}


@Composable
private fun NewsCategoryLabel(
    category: BriefingArticleCategory,
    ranks: Int
) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0x210072E7),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                horizontal = 9.dp,
                vertical = 4.dp
            )
    ) {
        Text(
            text = "${stringResource(id = category.getStringResId())} $ranks",
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 17.sp
            ),
            color = Color(0xFF0072E7)
        )
    }
}

@Composable
private fun NewsDate(
    date: String,
    gptModel: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 25.sp
            ),
            color = BriefingTheme.colorScheme.TextGray
        )

        Spacer(
            modifier = Modifier
                .size(
                    width = 1.dp,
                    height = 10.dp
                )
                .background(BriefingTheme.colorScheme.SeperatorGray)
        )

        Text(
            text = stringResource(id = R.string.generated_engine, gptModel),
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 25.sp
            ),
            color = BriefingTheme.colorScheme.TextGray
        )
    }
}

@Composable
private fun ScrapButton(
    modifier: Modifier = Modifier,
    isBookmarked: Boolean,
    onBookmark: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(40.dp))
            .clickable { onBookmark() },
        shape = RoundedCornerShape(40.dp),
        color = if (isBookmarked) {
            BriefingTheme.colorScheme.TextGray
        } else {
            Color(0xFF0072E7)
        }
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 35.dp,
                vertical = 10.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.scrap),
                style = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 30.sp
                ),
                color = BriefingTheme.colorScheme.BackgroundWhite
            )
        }
    }
}

@Composable
private fun RelatedNewsSection(
    modifier: Modifier = Modifier,
    relatedNewsList: List<BriefingArticleRelated>,
    onClickNews: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 21.dp)
    ) {
        Text(
            text = stringResource(id = R.string.related_articles),
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 25.sp
            ),
            color = BriefingTheme.colorScheme.TextBlack
        )

        Spacer(modifier = Modifier.height(10.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BriefingTheme.colorScheme.SeperatorGray)
        )

        Spacer(modifier = Modifier.height(10.dp))

        relatedNewsList.forEach { relatedNews ->
            RelatedNewsItem(
                relatedNews = relatedNews
            ) { url ->
                onClickNews(url)
            }

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
private fun RelatedNewsItem(
    modifier: Modifier = Modifier,
    relatedNews: BriefingArticleRelated,
    onClickNews: (String) -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                onClickNews(relatedNews.url)
            }
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(21.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = relatedNews.title,
                style = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 19.sp
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = BriefingTheme.colorScheme.TextBlack
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = relatedNews.press,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 17.sp
            ),
            color = BriefingTheme.colorScheme.TextGray
        )
    }
}

private fun TimeOfDay.getStringResId(): Int {
    return when (this) {
        TimeOfDay.MORNING -> R.string.time_of_day_morning
        TimeOfDay.EVENING -> R.string.time_of_day_evening
    }
}

private fun BriefingArticleCategory.getStringResId(): Int {
    return when (this) {
        BriefingArticleCategory.KOREA -> R.string.category_korea
        BriefingArticleCategory.GLOBAL -> R.string.category_global
        BriefingArticleCategory.SOCIAL -> R.string.category_social
        BriefingArticleCategory.SCIENCE -> R.string.category_science
        BriefingArticleCategory.ECONOMY -> R.string.category_economy
    }
}
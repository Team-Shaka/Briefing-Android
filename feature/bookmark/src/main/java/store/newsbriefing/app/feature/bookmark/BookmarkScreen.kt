package store.newsbriefing.app.feature.bookmark

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import store.newsbriefing.app.core.designsystem.LoadingDialog
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.core.designsystem.theme.Pretendard
import store.newsbriefing.app.core.model.Scrap
import store.newsbriefing.app.core.model.TimeOfDay

@Composable
internal fun BookmarkRoute(
    showSnackbar: (String) -> Unit,
    navigateUp: () -> Unit,
    navigateToNewsDetail: (String) -> Unit,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val uiState by bookmarkViewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    )

    LaunchedEffect(Unit) {
        bookmarkViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BookmarkEvent.ErrorOccurred -> {
                    showSnackbar(event.message)
                }
            }
        }
    }

    BookmarkScreen(
        uiState = uiState,
        showSnackbar = showSnackbar,
        navigateUp = navigateUp,
        navigateToNewsDetail = navigateToNewsDetail
    )
}

@Composable
internal fun BookmarkScreen(
    uiState: BookmarkUiState,
    showSnackbar: (String) -> Unit,
    navigateUp: () -> Unit,
    navigateToNewsDetail: (String) -> Unit
) {
    when (uiState.articles) {
        is BookmarkArticleUiState.Loading -> {
            LoadingDialog()
        }
        is BookmarkArticleUiState.Error -> {
            // Error
        }
        is BookmarkArticleUiState.Success -> {
            val bookmarkArticles = uiState.articles.data

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BriefingTheme.colorScheme.BackgroundWhite)
                    .verticalScroll(rememberScrollState())
            ) {
                TopBar {
                    navigateUp()
                }

                bookmarkArticles.forEach {
                    BookmarkSection(
                        date = it.date,
                        items = it.scraps,
                        onItemClick = navigateToNewsDetail
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 21.dp,
                vertical = 7.dp
            )
    ) {
        IconButton(onClick = onBack) {
            Icon(
                modifier = Modifier.size(33.dp),
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = stringResource(id = R.string.title_bookmark),
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 21.5.sp,
                color = BriefingTheme.colorScheme.TextBlack
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BookmarkSection(
    date: String,
    items: List<Scrap>,
    onItemClick: (String) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(
                    horizontal = 21.dp,
                    vertical = 13.dp
                ),
            text = date,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 16.5.sp,
                color = BriefingTheme.colorScheme.TextGray
            )
        )

        items.forEachIndexed { index, item ->
            BookmarkItem(item = item) {
                onItemClick("${item.briefingId}")
            }

            if (index != items.size - 1) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(BriefingTheme.colorScheme.SeperatorGray)
                )
            } else {
                Spacer(modifier = Modifier.height(19.dp))
            }
        }
    }

}

@Composable
private fun BookmarkItem(
    item: Scrap,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(item.briefingId)
            }
            .padding(
                start = 21.dp,
                end = 21.dp,
                top = 22.dp,
                bottom = 14.dp
            )
    ) {
        Text(
            text = item.title,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 21.5.sp,
                color = BriefingTheme.colorScheme.TextBlack
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.subtitle,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 19.sp,
                color = BriefingTheme.colorScheme.TextGray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${item.date} ${stringResource(id = item.timeOfDay.getStringResId())}",
                style = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 14.5.sp,
                    color = BriefingTheme.colorScheme.TextGray
                )
            )

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(10.dp)
                    .background(BriefingTheme.colorScheme.SeperatorGray)
            )

            Text(
                text = stringResource(id = R.string.generated_engine, item.gptModel),
                style = TextStyle(
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 14.5.sp,
                    color = BriefingTheme.colorScheme.TextGray
                )
            )
        }
    }
}

private fun TimeOfDay.getStringResId(): Int {
    return when (this) {
        TimeOfDay.MORNING -> R.string.time_of_day_morning
        TimeOfDay.EVENING -> R.string.time_of_day_evening
    }
}
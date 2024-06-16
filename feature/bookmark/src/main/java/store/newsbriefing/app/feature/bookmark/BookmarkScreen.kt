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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.core.designsystem.theme.Pretendard
import store.newsbriefing.app.core.model.Scrap
import java.time.ZonedDateTime

@Composable
internal fun BookmarkRoute(
    showSnackbar: (String) -> Unit
) {
    BookmarkScreen(
        showSnackbar = showSnackbar
    )
}

@Preview
@Composable
fun BookmarkScrrenPreview() {
    BriefingTheme {
        BookmarkScreen(
            showSnackbar = {}
        )
    }
}

@Composable
internal fun BookmarkScreen(
    showSnackbar: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BriefingTheme.colorScheme.BackgroundWhite)
            .verticalScroll(rememberScrollState())
    ) {
        TopBar {

        }



        repeat(3) {
            BookmarkSection(
                date = "2023.11.01(수) 오후",
                items = listOf(
                    Scrap(
                        briefingId = 1,
                        ranks = 1,
                        title = "제목",
                        subtitle = "부제목",
                        date = ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZonedDateTime.now().zone),
                        timeOfDay = "오전 10:00",
                        gptModel = "GPT-3",
                    ),
                    Scrap(
                        briefingId = 2,
                        ranks = 2,
                        title = "제목",
                        subtitle = "부제목",
                        date = ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZonedDateTime.now().zone),
                        timeOfDay = "오전 10:00",
                        gptModel = "GPT-3",
                    ),
                    Scrap(
                        briefingId = 3,
                        ranks = 3,
                        title = "제목",
                        subtitle = "부제목",
                        date = ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZonedDateTime.now().zone),
                        timeOfDay = "오전 10:00",
                        gptModel = "GPT-3",
                    )
                )
            )
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
            text = "보관함",
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
    items: List<Scrap>
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
                text = "${item.date} ${item.timeOfDay}",
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
                text = "${item.gptModel}로 생서됨",
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
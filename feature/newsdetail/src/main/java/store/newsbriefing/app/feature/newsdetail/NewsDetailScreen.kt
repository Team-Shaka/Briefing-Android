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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.core.designsystem.theme.Pretendard
import store.newsbriefing.app.core.model.BriefingArticleRelated

@Composable
internal fun NewsDetailRoute(
    showSnackbar: (String) -> Unit
) {
    NewsDetailScreen(
        showSnackbar = showSnackbar
    )
}

@Preview
@Composable
fun NewsDetailScreenPreview() {
    BriefingTheme {
        NewsDetailScreen(
            showSnackbar = {}
        )
    }
}

@Composable
internal fun NewsDetailScreen(
    showSnackbar: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BriefingTheme.colorScheme.BackgroundWhite)
    ) {
        TopBar(onBack = {  }) {
            
        }

        NewsDetailHeader(
            title = "배터리 혁명",
            category = "사회 1"
        )

        NewsSummarySection(
            title = "2차 전지 혁명으로 인한 놀라운 발견과 문제 해결",
            content = "배터리 혁명은 현대 산업과 일상 생활에 혁명적인 변화를 가져왔다. 전기 자동차 및 이동식 장치들은 더 큰 용량과 효율성을 가진 배터리로 긴 주행거리와 높은 성능을 실현하였다. 또한 재생 에너지 저장 시스템으로 활용되어 전력 그리드 안정성을 증진시키고 친환경 에너지 전환을 촉진하고 있다. 연구의 진보로 배터리 수명과 충전 시간이 개선되며, 이는 모바일 기기부터 심지어 대규모 에너지 저장까지 다양한 분야에서 혁신을 이뤄내고 있다",
        )

        Spacer(modifier = Modifier.height(65.dp))
        
        ScrapButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            isBookmarked = false
        ) {
            
        }

        RelatedNewsSection(
            relatedNewsList = listOf(
                BriefingArticleRelated(
                    id = 1,
                    press = "KBS충북",
                    title = "배터리 혁명은 현대 산업과 일상 생활에 혁명적인 변화를 가져왔다.",
                    url = "https://naver.com"
                ),
                BriefingArticleRelated(
                    id = 2,
                    press = "KBS충북",
                    title = "배터리 혁명은 현대 산업과 일상 생활에 혁명적인 변화를 가져왔다.",
                    url = "https://naver.com"
                ),
            )
        ) { url ->
            val webPage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            startActivity(context, intent, null)
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
    category: String
) {
    Column(
        modifier = modifier
            .padding(horizontal = 21.dp)
    ) {
        Spacer(modifier = Modifier.height(13.dp))

        NewsCategoryLabel(category = category)

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

        NewsDate(date = "2023.10.31 아침")

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
    category: String
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
            text = category,
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
    date: String
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
            text = "GPT-3로 생성됨",
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
                text = "+ 스크랩",
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
        modifier = Modifier.padding(horizontal = 21.dp)
    ) {
        Text(
            text = "관련 기사",
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
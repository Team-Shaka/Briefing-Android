package store.newsbriefing.app.feature.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
internal fun SettingRoute(
    showSnackbar: (String) -> Unit
) {
    SettingScreen(
        showSnackbar = showSnackbar
    )
}

@Preview
@Composable
fun SettingScrrenPreview() {
    BriefingTheme {
        SettingScreen(
            showSnackbar = {}
        )
    }
}

@Composable
internal fun SettingScreen(
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

        SettingTitle("구독 서비스")
        SettingItem("Briefing Premium") {

        }

        SettingTitle("앱 정보")
        AppVersionItem("2.0.0")
        SettingItem("피드백 및 문의하기") {

        }
        SettingItem("버전 노트") {

        }

        SettingTitle("개인 정보 보호")
        SettingItem("이용 약관") {

        }
        SettingItem("개인정보처리방침") {

        }
        SettingItem("유의 사항") {

        }

        SettingTitle("회원 관리")
        SettingItem("로그아웃") {

        }
        SettingItem(
            "회원 탈퇴",
            BriefingTheme.colorScheme.TextRed
        ) {

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
            text = "설정",
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
private fun SettingTitle(
    title: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8))
            .padding(
                start = 30.dp,
                top = 28.dp,
                bottom = 8.dp
            ),
        text = title,
        style = BriefingTheme.typography.ContextStyleRegular,
        color = BriefingTheme.colorScheme.TextBlack
    )
}

@Composable
private fun SettingItem(
    menu: String,
    textColor: Color = BriefingTheme.colorScheme.TextBlack,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                start = 30.dp,
                end = 10.dp,
                top = 12.dp,
                bottom = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = menu,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                lineHeight = 30.sp,
                color = textColor
            )
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null
        )
    }
}

@Composable
private fun AppVersionItem(
    version: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 30.dp,
                end = 19.dp,
                top = 12.dp,
                bottom = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text ="앱 버전",
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                lineHeight = 30.sp,
                color = BriefingTheme.colorScheme.TextBlack
            )
        )

        Text(
            text = version,
            style = TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                lineHeight = 30.sp,
                color = BriefingTheme.colorScheme.PrimaryBlue
            )
        )
    }
}
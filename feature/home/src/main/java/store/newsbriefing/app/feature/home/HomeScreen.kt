package store.newsbriefing.app.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme

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

val topics = listOf("사회", "과학","글로벌", "경제")

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

        val pagerState = rememberPagerState(pageCount = { topics.size })
        val scope = rememberCoroutineScope()

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
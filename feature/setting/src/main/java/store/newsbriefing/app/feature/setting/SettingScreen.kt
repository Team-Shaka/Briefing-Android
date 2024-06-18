package store.newsbriefing.app.feature.setting

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.core.designsystem.theme.Pretendard

@Composable
internal fun SettingRoute(
    showSnackbar: (String) -> Unit,
    navigateUp: () -> Unit,
    navigateToSignIn: () -> Unit,
    appVersion: String,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        settingViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SettingEvent.ErrorOccurred -> {
                    showSnackbar(event.message)
                }
                is SettingEvent.Logout, is SettingEvent.DeleteMember -> {
                    navigateToSignIn()
                }
            }
        }
    }

    SettingScreen(
        showSnackbar = showSnackbar,
        navigateUp = navigateUp,
        logout = settingViewModel::logout,
        deleteMember = settingViewModel::deleteMember,
        appVersion = appVersion
    )
}

@Composable
internal fun SettingScreen(
    showSnackbar: (String) -> Unit,
    navigateUp: () -> Unit,
    logout: () -> Unit,
    deleteMember: () -> Unit,
    appVersion: String
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BriefingTheme.colorScheme.BackgroundWhite)
            .verticalScroll(rememberScrollState())
    ) {
        TopBar {
            navigateUp()
        }

        SettingTitle(stringResource(id = R.string.subscription_service))
        SettingItem(stringResource(id = R.string.briefing_premium)) {

        }

        SettingTitle(stringResource(id = R.string.app_information))
        AppVersionItem(appVersion)
        SettingItem(stringResource(id = R.string.feedback_and_inquiry)) {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/HQXmEBkQ6wyW9jiw7"))
            startActivity(context, intent, null)
        }
        SettingItem(stringResource(id = R.string.version_note)) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://onve.notion.site/Briefing-8af692ff041c4fc6931b2fc897411e6d?pvs=4")
            )
            startActivity(context, intent, null)
        }

        SettingTitle(stringResource(id = R.string.privacy_policy))
        SettingItem(stringResource(id = R.string.terms_of_service)) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://sites.google.com/view/brieifinguse/%ED%99%88")
            )
            startActivity(context, intent, null)
        }
        SettingItem(stringResource(id = R.string.data_processing_policy)) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://sites.google.com/view/briefing-private/%ED%99%88")
            )
            startActivity(context, intent, null)
        }
        SettingItem(stringResource(id = R.string.precautions)) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://onve.notion.site/Briefing-e1cb17e2e7c54d3b9a7036b29ee9b11a?pvs=4")
            )
            startActivity(context, intent, null)
        }

        SettingTitle(stringResource(id = R.string.user_management))
        SettingItem(stringResource(id = R.string.logout)) {
            logout()
        }
        SettingItem(
            stringResource(id = R.string.withdrawal),
            BriefingTheme.colorScheme.TextRed
        ) {
            deleteMember()
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
            text = stringResource(id = R.string.setting_title),
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
            text = stringResource(id = R.string.app_version),
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
package store.newsbriefing.app.feature.auth.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.feature.auth.R

@Composable
fun SignInRoute() {
    SignInScreen()
}

@Preview
@Composable
fun SignInScreen() {

}

@Preview
@Composable
fun SignInTitlePreview() {
    BriefingTheme {
        SignInTitle(Modifier.background(BriefingTheme.colorScheme.PrimaryBlue))
    }
}

@Composable
fun SignInTitle(modifier : Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = "Briefing", style = BriefingTheme.typography.TitleStyleBold.copy(
                color = BriefingTheme.colorScheme.BackgroundWhite,
                fontSize = 70.sp,
                fontWeight = FontWeight.W700
            )
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Your Keyword Newskeeper",
            style = BriefingTheme.typography.SubtitleStyleRegular.copy(
                color = BriefingTheme.colorScheme.BackgroundWhite,
                fontSize = 25.sp,
                fontWeight = FontWeight.W400
            )
        )

        Spacer(Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.powered_by_gpt4),
            contentDescription = null
        )
    }
}
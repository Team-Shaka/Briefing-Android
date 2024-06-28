package store.newsbriefing.app.feature.auth.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.core.ui.BuildConfig
import store.newsbriefing.app.feature.auth.R
import java.util.UUID

private fun createGoogleIdOption(): GetGoogleIdOption {
    return GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
        .setAutoSelectEnabled(true)
        //.setNonce(generateNonce())
        .build()
}

@Composable
fun SignInRoute(
    showSnackBar : (String) -> Unit,
    navigateToMain : () -> Unit,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val composeCoroutine = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        signInViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SignInEvent.NavigateToMain -> {
                    navigateToMain()
                }
                is SignInEvent.ErrorOccurred -> {
                    showSnackBar(event.message)
                }
            }
        }
    }

    SignInScreen {
        val googleIdOption = createGoogleIdOption()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        composeCoroutine.launch {
            try {
                val req = CredentialManager.create(context).getCredential(context, request)
                signInViewModel.handleSignIn(req)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    BriefingTheme {
        SignInScreen()
    }
}

private fun generateNonce(): String {
    // Nonce 생성 로직
    return UUID.randomUUID().toString()
}

@Composable
fun SignInScreen(onGoogleSignInRequest: () -> Unit = {}) {

    Column(
        Modifier
            .fillMaxSize()
            .background(color = BriefingTheme.colorScheme.PrimaryBlue),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            SignInTitle(Modifier.align(Alignment.Center))
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(36.dp, 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInWithGoogleButton(Modifier.fillMaxWidth(), onGoogleSignInRequest)

            Spacer(modifier = Modifier.height(40.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp)
                    .background(Color(0xffb6b6b6))
            )

            Text(
                modifier = Modifier.padding(24.dp),
                text = "나중에 하기",
                style = BriefingTheme.typography.ContextStyleBold.copy(color = BriefingTheme.colorScheme.BackgroundWhite)
            )
        }
    }
}

@Preview
@Composable
fun SignInTitlePreview() {
    BriefingTheme {
        SignInTitle(Modifier.background(BriefingTheme.colorScheme.PrimaryBlue))
    }
}

@Composable
fun SignInTitle(modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
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
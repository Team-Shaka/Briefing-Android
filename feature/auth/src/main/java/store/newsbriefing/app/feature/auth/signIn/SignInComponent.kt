package store.newsbriefing.app.feature.auth.signIn

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme
import store.newsbriefing.app.feature.auth.R


@Composable
@Preview
fun SignInWithGoogleButtonPreview() {
    BriefingTheme {
        SignInWithGoogleButton(Modifier.fillMaxWidth())
    }
}

@Composable
fun SignInWithGoogleButton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(
                color = BriefingTheme.colorScheme.BackgroundWhite,
                shape = RoundedCornerShape(25.dp)
            )
            .padding(24.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(width = 24.dp, height = 25.dp),
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = null
        )

        Spacer(Modifier.width(10.dp))

        Text(text = "Sign in with Google", style = BriefingTheme.typography.SmallcontextStyleBold)
    }
}
package store.newsbriefing.app.core.designsystem

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        CircularProgressIndicator(
            color = BriefingTheme.colorScheme.PrimaryBlue
        )
    }
}
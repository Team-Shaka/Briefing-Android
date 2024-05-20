package store.newsbriefing.app.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class BriefingColor(
    val PrimaryBlue: Color,
    val TextBlack: Color,
    val TextGray: Color,
    val TextRed: Color,
    val SeperatorGray: Color,
    val BackgroundWhite: Color,
)

val localBriefingColor =
    BriefingColor(
        PrimaryBlue = Color(0xFF306DAB),
        TextBlack = Color(0xFF000000),
        TextGray = Color(0x997C7C7C),
        TextRed = Color(0xFFFF0000),
        SeperatorGray = Color(0xFFDADADA),
        BackgroundWhite = Color(0xFFFFFFFF),
    )


val LocalBriefingColor = staticCompositionLocalOf {
    BriefingColor(
        PrimaryBlue = Color.Unspecified,
        TextBlack = Color.Unspecified,
        TextGray = Color.Unspecified,
        TextRed = Color.Unspecified,
        SeperatorGray = Color.Unspecified,
        BackgroundWhite = Color.Unspecified,
    )
}
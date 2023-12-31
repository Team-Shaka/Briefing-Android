package com.dev.briefing.presentation.setting.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.briefing.R
import com.dev.briefing.presentation.theme.BriefingTheme

@Composable
fun PremiumText(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = BriefingTheme.color.PrimaryBlue,
                fontSize = 20.sp
            )
        ) {
            append("Briefing")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold, color = BriefingTheme.color.TextBlack, fontSize = 20.sp
            )
        ) {
            append(" Premium")
        }
    }
}

@Composable
fun PremiumTextWithTitle(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight(400), color = BriefingTheme.color.TextBlack, fontSize = 20.sp
            )
        ) {
            append("더 강력하고, 더 폭넓은 기능을\n")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = BriefingTheme.color.PrimaryBlue,
                fontSize = 20.sp
            )
        ) {
            append("Briefing")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold, color = BriefingTheme.color.TextBlack, fontSize = 20.sp
            )
        ) {
            append(" Premium")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight(400), color = BriefingTheme.color.TextBlack, fontSize = 20.sp
            )
        ) {
            append("으로 누려보세요.")
        }
    }
}

@Composable
fun PremiumSubScribeText(
    text: String,
    fontWeight: FontWeight = FontWeight(700),
    fontSize: TextUnit = 20.sp
) {
    val textSpan = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = fontWeight,
                color = BriefingTheme.color.PrimaryBlue,
                fontSize = fontSize
            )
        ) {
            append("Briefing")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = fontWeight, color = BriefingTheme.color.TextBlack, fontSize = fontSize
            )
        ) {
            append(" Premium")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = fontWeight, color = BriefingTheme.color.TextBlack, fontSize = fontSize
            )
        ) {
            append(text)
        }
    }
    Text(textSpan)
}

@Composable
fun PremiumFunctionItem(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.height(20.dp), painter = painterResource(
                id = R.drawable.ic_check_21
            ), contentDescription = "list item"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = text),
            style = BriefingTheme.typography.ContextStyleBold.copy(
                fontWeight = FontWeight(400), color = BriefingTheme.color.TextBlack
            ),
        )
    }
}

@Composable
fun PremiumPriceInfo(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    @StringRes price: Int,
    @StringRes subInfo: Int? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BriefingTheme.color.TextBlack,
            )
            .padding(vertical = 15.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PremiumSubScribeText(title)
        PremiumSubScribeText(subtitle, fontWeight = FontWeight(400), fontSize = 13.sp)
        Spacer(modifier = Modifier.height(11.dp))
        Text(
            stringResource(id = price), style = BriefingTheme.typography.TitleStyleBold.copy(
                color = BriefingTheme.color.TextBlack
            )
        )
        if (subInfo != null) {
            Text(
                stringResource(id = subInfo), style = BriefingTheme.typography.DetailStyleRegular.copy(
                    color = BriefingTheme.color.PrimaryBlue
                )
            )
        }
    }
}
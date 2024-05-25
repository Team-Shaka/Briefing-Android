package store.newsbriefing.app.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import store.newsbriefing.app.core.designsystem.NoRippleInteractionSource
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme

@Composable
private fun ScrollableTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color
) {
    Spacer(
        modifier = Modifier
            .width(indicatorWidth)
            .height(1.5.dp)
            .offset(indicatorOffset)
            .background(color = indicatorColor)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScrollableTabItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Surface(
            modifier = modifier,
            color = Color.Transparent,
            onClick = { onClick() },
            interactionSource = NoRippleInteractionSource()
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = text,
                style = BriefingTheme.typography.ContextStyleRegular,
                color = BriefingTheme.colorScheme.TextBlack
            )
        }
    }
}

@Composable
fun ScrollableTabRow(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItemIndex: Int,
    indicatorColor: Color = BriefingTheme.colorScheme.PrimaryBlue,
    onClick: (index: Int) -> Unit
) {
    val density = LocalDensity.current
    val sizeList = remember { mutableStateMapOf<Int, Dp>() }

    val indicatorOffset: Dp by animateDpAsState(
        targetValue = sizeList
            .values
            .take(selectedItemIndex)
            .sumOf { it } + 24.dp + 16.dp * selectedItemIndex,
        animationSpec = tween(easing = FastOutSlowInEasing)
    )
    val indicatorWidth: Dp by animateDpAsState(
        targetValue = sizeList[selectedItemIndex] ?: 0.dp,
        animationSpec = tween(easing = FastOutSlowInEasing)
    )

    Box(contentAlignment = Alignment.BottomStart) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(color = BriefingTheme.colorScheme.SeperatorGray)
        )

        ScrollableTabIndicator(
            indicatorWidth = indicatorWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = indicatorColor
        )

        Row(
            modifier = modifier
                .background(color = Color.Transparent)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items.forEachIndexed { index, text ->
                ScrollableTabItem(
                    modifier = Modifier.onSizeChanged {
                        sizeList[index] = with(density) { it.width.toDp() }
                    },
                    onClick = { onClick(index) },
                    text = text
                )
            }
        }
    }
}

private fun <T> Iterable<T>.sumOf(selector: (T) -> Dp): Dp {
    var sum: Dp = 0.dp
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

@Preview
@Composable
fun PreviewScrollableTabRow() {
    var selectedItemIndex by remember { mutableStateOf(0) }

    BriefingTheme {
        ScrollableTabRow(
            items = listOf("사회", "과학", "글로벌", "경제"),
            selectedItemIndex = selectedItemIndex,
            onClick = { selectedItemIndex = it }
        )
    }
}
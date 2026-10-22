package com.nkoexe.text.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

internal data class FittedFont(
    val size: TextUnit,
    val fitsAtMinimum: Boolean
)

/** Measures width and height using the same typeface used by the editor and display. */
@Composable
internal fun rememberFittedFontSize(
    text: String,
    availableWidth: Dp,
    availableHeight: Dp
): FittedFont {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val widthPx = with(density) { availableWidth.roundToPx() }.coerceAtLeast(1)
    val heightPx = with(density) { availableHeight.roundToPx() }.coerceAtLeast(1)
    val maximum = (availableHeight.value * 0.62f)
        .coerceAtLeast(FontFitPolicy.MINIMUM_FONT_SIZE_SP)
    val fontFamily = MaterialTheme.typography.bodyLarge.fontFamily

    val result = remember(text, widthPx, heightPx, density.fontScale, maximum, textMeasurer, fontFamily) {
        FontFitPolicy.largestFitting(maximum) { fontSize ->
            val layout = textMeasurer.measure(
                text = AnnotatedString(text),
                style = TextStyle(
                    fontFamily = fontFamily,
                    fontSize = fontSize.sp,
                    lineHeight = (fontSize * 1.08f).sp,
                    textAlign = TextAlign.Center
                ),
                constraints = Constraints(maxWidth = widthPx, maxHeight = heightPx),
                softWrap = true
            )
            !layout.hasVisualOverflow
        }
    }

    return FittedFont(size = result.fontSizeSp.sp, fitsAtMinimum = result.minimumFits)
}

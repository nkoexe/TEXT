package com.nkoexe.text.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    text: String,
    onTextChange: (String) -> Unit,
    onShow: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val ratio = remember(context) { landscapeDisplayAspectRatio(context) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeDrawingPadding()
            .imePadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        EditablePreview(
            text = text,
            onTextChange = onTextChange,
            aspectRatio = ratio,
            focusRequester = focusRequester,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f, fill = true))

        Button(
            onClick = {
                if (text.isNotBlank()) {
                    keyboardController?.hide()
                    onShow()
                }
            },
            enabled = text.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text(
                text = "SHOW",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun EditablePreview(
    text: String,
    onTextChange: (String) -> Unit,
    aspectRatio: Float,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .clip(shape)
            .background(Color.Black)
            .border(1.dp, Color(0xFF666666), shape)
    ) {
        val fitting = rememberFittedFontSize(text, maxHeight)

        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .padding(10.dp),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = fitting.size,
                textAlign = TextAlign.Center,
                lineHeight = (fitting.size.value * 1.08f).sp
            ),
            cursorBrush = androidx.compose.ui.graphics.SolidColor(Color.White),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            ),
            maxLines = Int.MAX_VALUE,
            onTextLayout = fitting.onTextLayout,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    innerTextField()
                }
            }
        )
    }
}

internal data class FittedFont(
    val size: TextUnit,
    val onTextLayout: (TextLayoutResult) -> Unit
)

@Composable
internal fun rememberFittedFontSize(text: String, availableHeight: Dp): FittedFont {
    val maximum = (availableHeight.value * 0.62f).coerceAtLeast(8f)
    val minimum = (availableHeight.value * 0.05f).coerceAtLeast(4f)
    var size by remember(text, availableHeight) { mutableFloatStateOf(maximum) }
    val onTextLayout: (TextLayoutResult) -> Unit = remember(size, minimum) {
        { layout ->
            if (layout.hasVisualOverflow && size > minimum) {
                size = maxOf(minimum, size * 0.9f)
            }
        }
    }
    return FittedFont(size = size.sp, onTextLayout = onTextLayout)
}

private fun landscapeDisplayAspectRatio(context: Context): Float {
    val metrics = context.resources.displayMetrics
    val longer = maxOf(metrics.widthPixels, metrics.heightPixels).toFloat()
    val shorter = minOf(metrics.widthPixels, metrics.heightPixels).toFloat().coerceAtLeast(1f)
    return longer / shorter
}

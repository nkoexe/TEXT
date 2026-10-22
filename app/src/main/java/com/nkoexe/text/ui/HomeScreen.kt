package com.nkoexe.text.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nkoexe.text.R
import com.nkoexe.text.data.HistoryRecord
import kotlin.math.abs

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    text: String,
    onTextChange: (String) -> Unit,
    onShow: () -> Unit,
    history: List<HistoryRecord>,
    onSelectHistory: (String) -> Unit,
    onDeleteHistory: (String) -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val ratio = remember(context) { landscapeDisplayAspectRatio(context) }
    val imeVisible = WindowInsets.isImeVisible
    val deleteThresholdPx = with(LocalDensity.current) { 72.dp.toPx() }
    var textOverflowsAtMinimum by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black)
            .safeDrawingPadding().imePadding().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        EditablePreview(
            text = text,
            onTextChange = onTextChange,
            aspectRatio = ratio,
            focusRequester = focusRequester,
            onOverflowChange = { textOverflowsAtMinimum = it },
            modifier = Modifier.fillMaxWidth()
        )

        if (textOverflowsAtMinimum) {
            Text(
                text = stringResource(R.string.text_overflow_warning),
                color = Color(0xFFFFC857),
                style = MaterialTheme.typography.labelSmall
            )
        }

        if (!imeVisible && history.isNotEmpty()) {
            Text(
                text = stringResource(R.string.text_history_section),
                color = Color.LightGray,
                style = MaterialTheme.typography.labelMedium
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(history, key = { it.text }) { item ->
                    var drag by remember(item.text) { mutableFloatStateOf(0f) }
                    Box(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF171717))
                            .pointerInput(item.text, deleteThresholdPx) {
                                detectHorizontalDragGestures(
                                    onHorizontalDrag = { _, amount -> drag += amount },
                                    onDragEnd = {
                                        if (abs(drag) > deleteThresholdPx) onDeleteHistory(item.text)
                                        drag = 0f
                                    }
                                )
                            }
                            .clickable { onSelectHistory(item.text) }
                    ) {
                        Text(
                            text = item.text.replace("\n", "  ↵  "),
                            color = Color.White,
                            maxLines = 2,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp)
                        )
                    }
                }
            }
        } else {
            Spacer(modifier = Modifier.weight(1f, fill = true))
        }

        Button(
            onClick = { if (text.isNotBlank()) { keyboardController?.hide(); onShow() } },
            enabled = text.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text(
                text = stringResource(R.string.text_show_action),
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
    onOverflowChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)
    BoxWithConstraints(
        modifier = modifier.aspectRatio(aspectRatio).clip(shape).background(Color.Black)
            .border(1.dp, Color(0xFF666666), shape)
    ) {
        val fitting = rememberFittedFontSize(
            text = text,
            availableWidth = (maxWidth - 20.dp).coerceAtLeast(1.dp),
            availableHeight = (maxHeight - 20.dp).coerceAtLeast(1.dp)
        )
        LaunchedEffect(fitting.fitsAtMinimum) {
            onOverflowChange(!fitting.fitsAtMinimum)
        }

        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxSize().focusRequester(focusRequester).padding(10.dp),
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
            decorationBox = { innerTextField ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { innerTextField() }
            }
        )
    }
}

private fun landscapeDisplayAspectRatio(context: Context): Float {
    val metrics = context.resources.displayMetrics
    val longer = maxOf(metrics.widthPixels, metrics.heightPixels).toFloat()
    val shorter = minOf(metrics.widthPixels, metrics.heightPixels).toFloat().coerceAtLeast(1f)
    return longer / shorter
}

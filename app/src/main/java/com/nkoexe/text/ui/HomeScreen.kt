package com.nkoexe.text.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nkoexe.text.R
import com.nkoexe.text.data.HistoryRecord

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
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
    val aspectRatio = remember(context) { landscapeDisplayAspectRatio(context) }
    val imeVisible = WindowInsets.isImeVisible
    var textOverflowsAtMinimum by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .imePadding()
    ) {
        val contentWidth = (maxWidth - 40.dp).coerceAtLeast(1.dp)
        val usableHeight = (maxHeight - 20.dp).coerceAtLeast(1.dp)
        val heightLimit = usableHeight * if (imeVisible) 0.40f else 0.32f
        val previewHeight = minOf(contentWidth / aspectRatio, heightLimit)
        val previewWidth = (previewHeight * aspectRatio).coerceAtMost(contentWidth)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HomeHeader()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(previewHeight),
                contentAlignment = Alignment.Center
            ) {
                EditablePreview(
                    text = text,
                    onTextChange = onTextChange,
                    focusRequester = focusRequester,
                    onOverflowChange = { textOverflowsAtMinimum = it },
                    modifier = Modifier
                        .width(previewWidth)
                        .height(previewHeight)
                )
            }

            if (textOverflowsAtMinimum) {
                Text(
                    text = stringResource(R.string.text_overflow_warning),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            if (!imeVisible && history.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.text_history_section),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.text_history_swipe_hint),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelSmall,
                        letterSpacing = 0.6.sp
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(history, key = { it.text }) { item ->
                        HistoryEntry(
                            item = item,
                            onSelect = { onSelectHistory(item.text) },
                            onDelete = { onDeleteHistory(item.text) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            } else {
                Spacer(Modifier.weight(1f))
            }

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
                    .height(64.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(
                    text = stringResource(R.string.text_show_action),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.4.sp
                )
            }
        }
    }
}

@Composable
private fun HomeHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
        Text(
            text = stringResource(R.string.text_app_name),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1.2).sp
            )
        )
        Text(
            text = stringResource(R.string.text_tagline),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryEntry(
    item: HistoryRecord,
    onSelect: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(24.dp)
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { totalDistance -> totalDistance * 0.30f }
    )

    LaunchedEffect(dismissState.settledValue) {
        if (dismissState.settledValue != SwipeToDismissBoxValue.Settled) {
            onDelete()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier.fillMaxWidth(),
        backgroundContent = {
            val direction = dismissState.dismissDirection
            val isSwipingToDelete = direction != SwipeToDismissBoxValue.Settled
            val backgroundColor by animateColorAsState(
                targetValue = if (isSwipingToDelete) {
                    MaterialTheme.colorScheme.errorContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                },
                animationSpec = spring(),
                label = "history swipe background"
            )
            val alignment = if (direction == SwipeToDismissBoxValue.StartToEnd) {
                Alignment.CenterStart
            } else {
                Alignment.CenterEnd
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape)
                    .background(backgroundColor)
                    .padding(horizontal = 24.dp),
                contentAlignment = alignment
            ) {
                if (isSwipingToDelete) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Text(
                            text = stringResource(R.string.text_delete_action),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .clickable(onClick = onSelect),
            shape = shape,
            color = MaterialTheme.colorScheme.surfaceContainer,
            tonalElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.text.replace("\n", "   ↵   "),
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "›",
                    modifier = Modifier.padding(start = 12.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@Composable
private fun EditablePreview(
    text: String,
    onTextChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onOverflowChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(30.dp)
    BoxWithConstraints(
        modifier = modifier
            .clip(shape)
            .background(Color.Black)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, shape)
    ) {
        val fitting = rememberFittedFontSize(
            text = text,
            availableWidth = (maxWidth - 24.dp).coerceAtLeast(1.dp),
            availableHeight = (maxHeight - 24.dp).coerceAtLeast(1.dp)
        )

        LaunchedEffect(fitting.fitsAtMinimum) {
            onOverflowChange(!fitting.fitsAtMinimum)
        }

        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .padding(12.dp),
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
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

private fun landscapeDisplayAspectRatio(context: android.content.Context): Float {
    val metrics = context.resources.displayMetrics
    val longer = maxOf(metrics.widthPixels, metrics.heightPixels).toFloat()
    val shorter = minOf(metrics.widthPixels, metrics.heightPixels).toFloat().coerceAtLeast(1f)
    return longer / shorter
}

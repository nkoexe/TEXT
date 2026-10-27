package com.nkoexe.text.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
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
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
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
        val horizontalPadding = if (maxWidth < 360.dp) 16.dp else 22.dp
        val contentWidth = (maxWidth - horizontalPadding * 2).coerceAtLeast(1.dp)
        // Keep preview geometry tied to the display aspect ratio, not keyboard visibility.
        val previewHeight = (contentWidth / aspectRatio).coerceAtLeast(1.dp)
        // Keep the list compact, especially while the keyboard reduces available space.
        val historyHeight = if (maxHeight < 560.dp) 124.dp else 176.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = MaterialTheme.shapes.large,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_text_color),
                            contentDescription = stringResource(R.string.text_tool_text_color),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = MaterialTheme.shapes.large,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_background_fill),
                            contentDescription = stringResource(R.string.text_tool_background),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = MaterialTheme.shapes.large,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_font),
                            contentDescription = stringResource(R.string.text_tool_font),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

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
                            .fillMaxWidth()
                            .height(previewHeight)
                    )
                }
            }

            if (textOverflowsAtMinimum) {
                Surface(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(R.string.text_overflow_warning),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            if (history.isNotEmpty()) {
                HistoryPanel(
                    history = history,
                    onSelectHistory = onSelectHistory,
                    onDeleteHistory = onDeleteHistory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(historyHeight)
                )
            }

            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Spacer(Modifier.weight(1f))
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
                        .height(56.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.text_show_action),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.2.sp
                            )
                        )
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryPanel(
    history: List<HistoryRecord>,
    onSelectHistory: (String) -> Unit,
    onDeleteHistory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.text_history_section),
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(0.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            itemsIndexed(history, key = { _, item -> item.text }) { index, item ->
                HistoryEntry(
                    item = item,
                    index = index,
                    onSelect = { onSelectHistory(item.text) },
                    onDelete = { onDeleteHistory(item.text) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryEntry(
    item: HistoryRecord,
    index: Int,
    onSelect: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = MaterialTheme.shapes.large
    val hapticFeedback = LocalHapticFeedback.current
    var thresholdHapticPlayed by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        // A slightly longer travel distance gives the gesture a deliberate, tactile commitment.
        positionalThreshold = { totalDistance -> totalDistance * 0.36f }
    )

    LaunchedEffect(dismissState) {
        snapshotFlow { dismissState.targetValue }.collect { target ->
            if (target == SwipeToDismissBoxValue.Settled) {
                thresholdHapticPlayed = false
            } else if (!thresholdHapticPlayed) {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
                thresholdHapticPlayed = true
            }
        }
    }

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
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow
                ),
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
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                if (isSwipingToDelete) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
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
                .clickable(role = Role.Button, onClick = onSelect),
            shape = shape,
            color = if (index == 0) {
                MaterialTheme.colorScheme.surfaceContainerHigh
            } else {
                MaterialTheme.colorScheme.surfaceContainer
            }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 11.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(5.dp)
                        .height(30.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == 0) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.tertiary
                        )
                )
                Text(
                    text = item.text.replace("\n", "   ↵   "),
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
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
    val shape = MaterialTheme.shapes.extraLarge
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
            cursorBrush = SolidColor(Color.White),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            ),
            maxLines = Int.MAX_VALUE,
            decorationBox = { innerTextField ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    innerTextField()
                }
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

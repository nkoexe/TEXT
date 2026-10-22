package com.nkoexe.text.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val TextDarkColors = darkColorScheme(
    primary = Color(0xFFE5E5E5),
    onPrimary = Color(0xFF111111),
    secondary = Color(0xFFBDBDBD),
    onSecondary = Color(0xFF111111),
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF333333),
    onSurfaceVariant = Color(0xFFBDBDBD)
)

@Composable
fun TextTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TextDarkColors,
        content = content
    )
}

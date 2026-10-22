package com.nkoexe.text.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nkoexe.text.R

private val OutfitFontFamily = FontFamily(Font(R.font.outfit))
private val DefaultTypography = Typography()

private val OutfitTypography = Typography(
    displayLarge = DefaultTypography.displayLarge.copy(fontFamily = OutfitFontFamily, fontWeight = FontWeight.Bold),
    displayMedium = DefaultTypography.displayMedium.copy(fontFamily = OutfitFontFamily, fontWeight = FontWeight.Bold),
    displaySmall = DefaultTypography.displaySmall.copy(fontFamily = OutfitFontFamily, fontWeight = FontWeight.Bold),
    headlineLarge = DefaultTypography.headlineLarge.copy(fontFamily = OutfitFontFamily),
    headlineMedium = DefaultTypography.headlineMedium.copy(fontFamily = OutfitFontFamily),
    headlineSmall = DefaultTypography.headlineSmall.copy(fontFamily = OutfitFontFamily),
    titleLarge = DefaultTypography.titleLarge.copy(fontFamily = OutfitFontFamily),
    titleMedium = DefaultTypography.titleMedium.copy(fontFamily = OutfitFontFamily),
    titleSmall = DefaultTypography.titleSmall.copy(fontFamily = OutfitFontFamily),
    bodyLarge = DefaultTypography.bodyLarge.copy(fontFamily = OutfitFontFamily),
    bodyMedium = DefaultTypography.bodyMedium.copy(fontFamily = OutfitFontFamily),
    bodySmall = DefaultTypography.bodySmall.copy(fontFamily = OutfitFontFamily),
    labelLarge = DefaultTypography.labelLarge.copy(fontFamily = OutfitFontFamily),
    labelMedium = DefaultTypography.labelMedium.copy(fontFamily = OutfitFontFamily),
    labelSmall = DefaultTypography.labelSmall.copy(fontFamily = OutfitFontFamily)
)

private val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(10.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

private val TextDarkColors = darkColorScheme(
    primary = Color(0xFFD8C9FF),
    onPrimary = Color(0xFF211A30),
    primaryContainer = Color(0xFF3A3150),
    onPrimaryContainer = Color(0xFFECE2FF),
    secondary = Color(0xFFC9C3D9),
    onSecondary = Color(0xFF302D3A),
    secondaryContainer = Color(0xFF3A3645),
    onSecondaryContainer = Color(0xFFE5DEF4),
    tertiary = Color(0xFFFFB9CB),
    onTertiary = Color(0xFF3F1E29),
    tertiaryContainer = Color(0xFF5A303D),
    onTertiaryContainer = Color(0xFFFFD9E1),
    background = Color(0xFF101015),
    onBackground = Color(0xFFF4F0F7),
    surface = Color(0xFF15141A),
    onSurface = Color(0xFFEAE6F0),
    surfaceVariant = Color(0xFF2D2933),
    onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceContainerLowest = Color(0xFF0B0A0F),
    surfaceContainerLow = Color(0xFF19171F),
    surfaceContainer = Color(0xFF1E1C25),
    surfaceContainerHigh = Color(0xFF282630),
    surfaceContainerHighest = Color(0xFF32303B),
    outline = Color(0xFF938C9B),
    outlineVariant = Color(0xFF48434E),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF5C161B),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun TextTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TextDarkColors,
        typography = OutfitTypography,
        shapes = ExpressiveShapes,
        content = content
    )
}

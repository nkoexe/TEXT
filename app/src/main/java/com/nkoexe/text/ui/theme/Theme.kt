package com.nkoexe.text.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nkoexe.text.R

// Use the raw variable TTF, not the XML family wrapper. The wrapper only declared nominal
// weights for the same file without setting its "wght" axis; Compose therefore did not get
// explicit variable-font instances for requested weights.
@OptIn(ExperimentalTextApi::class)
private val OutfitFontFamily = FontFamily(
    Font(R.font.outfit_variable, weight = FontWeight.Thin, variationSettings = FontVariation.Settings(FontVariation.weight(100))),
    Font(R.font.outfit_variable, weight = FontWeight.ExtraLight, variationSettings = FontVariation.Settings(FontVariation.weight(200))),
    Font(R.font.outfit_variable, weight = FontWeight.Light, variationSettings = FontVariation.Settings(FontVariation.weight(300))),
    Font(R.font.outfit_variable, weight = FontWeight.Normal, variationSettings = FontVariation.Settings(FontVariation.weight(400))),
    Font(R.font.outfit_variable, weight = FontWeight.Medium, variationSettings = FontVariation.Settings(FontVariation.weight(500))),
    Font(R.font.outfit_variable, weight = FontWeight.SemiBold, variationSettings = FontVariation.Settings(FontVariation.weight(600))),
    Font(R.font.outfit_variable, weight = FontWeight.Bold, variationSettings = FontVariation.Settings(FontVariation.weight(700))),
    Font(R.font.outfit_variable, weight = FontWeight.ExtraBold, variationSettings = FontVariation.Settings(FontVariation.weight(800))),
    Font(R.font.outfit_variable, weight = FontWeight.Black, variationSettings = FontVariation.Settings(FontVariation.weight(900)))
)
private val DefaultTypography = Typography()

private val OutfitTypography = Typography(
    displayLarge = DefaultTypography.displayLarge.copy(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-1.0).sp
    ),
    displayMedium = DefaultTypography.displayMedium.copy(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.6).sp
    ),
    displaySmall = DefaultTypography.displaySmall.copy(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.4).sp
    ),
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
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(26.dp),
    extraLarge = RoundedCornerShape(34.dp)
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

private val TextLightColors = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFF8A4F63),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFD8E4),
    onTertiaryContainer = Color(0xFF31111D),
    background = Color(0xFFF8F5FC),
    onBackground = Color(0xFF1B1B21),
    surface = Color(0xFFF8F5FC),
    onSurface = Color(0xFF1B1B21),
    surfaceVariant = Color(0xFFE7E0EB),
    onSurfaceVariant = Color(0xFF49454F),
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFF2EDF7),
    surfaceContainer = Color(0xFFEDE7F2),
    surfaceContainerHigh = Color(0xFFE7E1EC),
    surfaceContainerHighest = Color(0xFFE1DBE6),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B)
)

@Composable
fun TextTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme ->
            dynamicDarkColorScheme(context)
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            dynamicLightColorScheme(context)
        darkTheme -> TextDarkColors
        else -> TextLightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = OutfitTypography,
        shapes = ExpressiveShapes,
        content = content
    )
}

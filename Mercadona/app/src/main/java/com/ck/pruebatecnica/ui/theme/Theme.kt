package com.ck.pruebatecnica.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColors = lightColorScheme(
    primary = Color(0xFF3A1F1A),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF2D2D0),
    onPrimaryContainer = Color(0xFF3A1F1A),
    secondary = Color(0xFF3A1F1A),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEFEFEF),
    onSecondaryContainer = Color(0xFF3A1F1A),
    background = Color(0xFFF2D2D0),
    onBackground = Color(0xFF3A1F1A),
    surface = Color(0xFFEFEFEF),
    onSurface = Color(0xFF3A1F1A),
    outline = Color(0xFF797979)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFD7B3B0),
    onPrimary = Color(0xFF3A1F1A),
    primaryContainer = Color(0xFF3A1F1A),
    onPrimaryContainer = Color(0xFFF2D2D0),
    secondary = Color(0xFFB59D9B),
    onSecondary = Color(0xFF3A1F1A),
    secondaryContainer = Color(0xFF3A1F1A),
    onSecondaryContainer = Color(0xFFD7B3B0),
    background = Color(0xFF3A1F1A),
    onBackground = Color(0xFFF2D2D0),
    surface = Color(0xFF3A1F1A),
    onSurface = Color(0xFFF2D2D0),
    outline = Color(0xFFB59D9B)
)

@Composable
fun PruebaTecnicaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
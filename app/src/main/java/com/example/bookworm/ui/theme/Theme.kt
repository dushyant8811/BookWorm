package com.example.bookworm.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- START OF CHANGES ---

// 1. The DarkColorScheme variable has been completely removed.

// 2. The LightColorScheme remains as our single source of truth for colors.
private val LightColorScheme = lightColorScheme(
    primary = DarkGray,
    onPrimary = White,
    secondary = MediumGray,
    onSecondary = White,
    surface = LightGray,
    onSurface = DarkGray,
    surfaceContainer = White,
    background = LightGray,
    onBackground = DarkGray,
    surfaceVariant = GraySurface,
    onSurfaceVariant = DarkGray,
    error = Color(0xFFB00020),
    onError = Color.White
)


@Composable
fun BookWormTheme(
    // 3. The `darkTheme` parameter is removed. The function no longer checks system settings.
    content: @Composable () -> Unit
) {
    // 4. The colorScheme is now ALWAYS set to LightColorScheme.
    val colorScheme = LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()

            // 5. Status bar icons are now ALWAYS set for a light theme (i.e., dark icons).
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
// --- END OF CHANGES ---
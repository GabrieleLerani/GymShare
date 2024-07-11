package com.project.gains.theme




import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = lightColorScheme(
    primary = GOrange,
    onPrimary = GBlack,
    inversePrimary = GOrange,
    primaryContainer = GBlack,
    onPrimaryContainer = GWhite,
    background = GBlack,
    onBackground = GWhite,
    secondary = GGreen,
    onSecondary = GGreen,
    secondaryContainer = GWarning,
    onSecondaryContainer = GWhite,
    tertiary = GBlue,
    onTertiary = GLightOrange,
    tertiaryContainer = GBlue,
    onTertiaryContainer = GWhite,
    surface = GBlack, // Bottom bar background color
    onSurface = GWhite, // Bottom bar text/icon color
    error = GRed,
    onError = GBlack,
    errorContainer = GRed,
    onErrorContainer = GRed,
)

private val lightColorScheme = lightColorScheme(
    primary = GOrange,
    onPrimary = GWhite,
    inversePrimary = GOrange,
    primaryContainer = GWhite,
    onPrimaryContainer = GBlack,
    background = GWhite,
    onBackground = GBlack,
    secondary = GGreen,
    onSecondary = GGreen,
    secondaryContainer = GWarning,
    onSecondaryContainer = GBlack,
    tertiary = GBlue,
    onTertiary = GLightOrange,
    tertiaryContainer = GBlue,
    onTertiaryContainer = GBlack,
    surface = GWhite, // Bottom bar background color
    onSurface = GBlack, // Bottom bar text/icon color
    error = GRed,
    onError = GWhite,
    errorContainer = GRed,
    onErrorContainer = GRed,
)


@Composable
fun GainsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
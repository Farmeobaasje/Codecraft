package nl.codecraft.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nl.codecraft.model.ThemeOptions
import nl.codecraft.model.ThemeStyle

private val GitHubDarkColorScheme = darkColorScheme(
    primary = GitHubDarkAccentGreen,
    onPrimary = Color.White,
    primaryContainer = GitHubDarkSurface2,
    onPrimaryContainer = GitHubDarkPrimaryText,
    secondary = GitHubDarkSecondaryText,
    onSecondary = Color.White,
    secondaryContainer = GitHubDarkSurface2,
    onSecondaryContainer = GitHubDarkPrimaryText,
    tertiary = GitHubDarkWarningOrange,
    onTertiary = Color.White,
    tertiaryContainer = GitHubDarkSurface2,
    onTertiaryContainer = GitHubDarkPrimaryText,
    background = GitHubDarkBackground,
    onBackground = GitHubDarkPrimaryText,
    surface = GitHubDarkSurface1,
    onSurface = GitHubDarkPrimaryText,
    surfaceVariant = GitHubDarkSurface2,
    onSurfaceVariant = GitHubDarkSecondaryText,
    outline = GitHubDarkBorder,
    outlineVariant = GitHubDarkSurface3,
    scrim = Color.Black.copy(alpha = 0.5f),
    error = GitHubDarkErrorRed,
    onError = Color.White,
    errorContainer = GitHubDarkSurface2,
    onErrorContainer = GitHubDarkErrorRed
)

private val GitHubLightColorScheme = lightColorScheme(
    primary = GitHubLightAccentGreen,
    onPrimary = Color.White,
    primaryContainer = GitHubLightSurface2,
    onPrimaryContainer = GitHubLightPrimaryText,
    secondary = GitHubLightSecondaryText,
    onSecondary = Color.White,
    secondaryContainer = GitHubLightSurface2,
    onSecondaryContainer = GitHubLightPrimaryText,
    tertiary = GitHubLightWarningOrange,
    onTertiary = Color.White,
    tertiaryContainer = GitHubLightSurface2,
    onTertiaryContainer = GitHubLightPrimaryText,
    background = GitHubLightBackground,
    onBackground = GitHubLightPrimaryText,
    surface = GitHubLightSurface1,
    onSurface = GitHubLightPrimaryText,
    surfaceVariant = GitHubLightSurface2,
    onSurfaceVariant = GitHubLightSecondaryText,
    outline = GitHubLightBorder,
    outlineVariant = GitHubLightSurface3,
    scrim = Color.Black.copy(alpha = 0.5f),
    error = GitHubLightErrorRed,
    onError = Color.White,
    errorContainer = GitHubLightSurface2,
    onErrorContainer = GitHubLightErrorRed
)

private val CodeCraftDarkColorScheme = darkColorScheme(
    primary = CodeCraftDarkAccentPurple,
    onPrimary = Color.White,
    primaryContainer = CodeCraftDarkSurface2,
    onPrimaryContainer = CodeCraftDarkPrimaryText,
    secondary = CodeCraftDarkSecondaryText,
    onSecondary = Color.White,
    secondaryContainer = CodeCraftDarkSurface2,
    onSecondaryContainer = CodeCraftDarkPrimaryText,
    tertiary = CodeCraftDarkWarningOrange,
    onTertiary = Color.White,
    tertiaryContainer = CodeCraftDarkSurface2,
    onTertiaryContainer = CodeCraftDarkPrimaryText,
    background = CodeCraftDarkBackground,
    onBackground = CodeCraftDarkPrimaryText,
    surface = CodeCraftDarkSurface1,
    onSurface = CodeCraftDarkPrimaryText,
    surfaceVariant = CodeCraftDarkSurface2,
    onSurfaceVariant = CodeCraftDarkSecondaryText,
    outline = CodeCraftDarkBorder,
    outlineVariant = CodeCraftDarkSurface3,
    scrim = Color.Black.copy(alpha = 0.5f),
    error = CodeCraftDarkErrorRed,
    onError = Color.White,
    errorContainer = CodeCraftDarkSurface2,
    onErrorContainer = CodeCraftDarkErrorRed
)

private val CodeCraftLightColorScheme = lightColorScheme(
    primary = CodeCraftLightAccentPurple,
    onPrimary = Color.White,
    primaryContainer = CodeCraftLightSurface2,
    onPrimaryContainer = CodeCraftLightPrimaryText,
    secondary = CodeCraftLightSecondaryText,
    onSecondary = Color.White,
    secondaryContainer = CodeCraftLightSurface2,
    onSecondaryContainer = CodeCraftLightPrimaryText,
    tertiary = CodeCraftLightWarningOrange,
    onTertiary = Color.White,
    tertiaryContainer = CodeCraftLightSurface2,
    onTertiaryContainer = CodeCraftLightPrimaryText,
    background = CodeCraftLightBackground,
    onBackground = CodeCraftLightPrimaryText,
    surface = CodeCraftLightSurface1,
    onSurface = CodeCraftLightPrimaryText,
    surfaceVariant = CodeCraftLightSurface2,
    onSurfaceVariant = CodeCraftLightSecondaryText,
    outline = CodeCraftLightBorder,
    outlineVariant = CodeCraftLightSurface3,
    scrim = Color.Black.copy(alpha = 0.5f),
    error = CodeCraftLightErrorRed,
    onError = Color.White,
    errorContainer = CodeCraftLightSurface2,
    onErrorContainer = CodeCraftLightErrorRed
)

@Composable
fun CodeCraftTheme(
    themeOption: ThemeOptions = ThemeOptions.SYSTEM,
    themeStyle: ThemeStyle = ThemeStyle.GITHUB,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeOption) {
        ThemeOptions.LIGHT -> false
        ThemeOptions.DARK -> true
        ThemeOptions.SYSTEM -> isSystemInDarkTheme()
    }
    
    val colorScheme = when (themeStyle) {
        ThemeStyle.GITHUB -> if (darkTheme) GitHubDarkColorScheme else GitHubLightColorScheme
        ThemeStyle.CODECRAFT_PREMIUM -> if (darkTheme) CodeCraftDarkColorScheme else CodeCraftLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

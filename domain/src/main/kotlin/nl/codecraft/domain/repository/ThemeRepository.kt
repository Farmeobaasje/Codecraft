package nl.codecraft.domain.repository

import kotlinx.coroutines.flow.Flow
import nl.codecraft.model.ThemeOptions
import nl.codecraft.model.ThemeStyle

/**
 * Repository for managing app theme preferences.
 */
interface ThemeRepository {
    /**
     * Get the current theme option as a Flow.
     */
    fun getThemeOption(): Flow<ThemeOptions>

    /**
     * Get the current theme style as a Flow.
     */
    fun getThemeStyle(): Flow<ThemeStyle>

    /**
     * Update the theme option.
     */
    suspend fun updateThemeOption(themeOption: ThemeOptions)

    /**
     * Update the theme style.
     */
    suspend fun updateThemeStyle(themeStyle: ThemeStyle)

    /**
     * Get the default GitHub username as a Flow.
     */
    fun getDefaultUsername(): Flow<String?>

    /**
     * Update the default GitHub username.
     */
    suspend fun updateDefaultUsername(username: String?)
}

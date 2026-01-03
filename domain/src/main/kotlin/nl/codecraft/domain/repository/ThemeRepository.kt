package nl.codecraft.domain.repository

import kotlinx.coroutines.flow.Flow
import nl.codecraft.model.ThemeOptions

/**
 * Repository for managing app theme preferences.
 */
interface ThemeRepository {
    /**
     * Get the current theme option as a Flow.
     */
    fun getThemeOption(): Flow<ThemeOptions>

    /**
     * Update the theme option.
     */
    suspend fun updateThemeOption(themeOption: ThemeOptions)
}

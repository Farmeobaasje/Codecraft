package nl.codecraft.data.repository

import kotlinx.coroutines.flow.Flow
import nl.codecraft.data.local.SettingsDataStore
import nl.codecraft.domain.repository.ThemeRepository
import nl.codecraft.model.ThemeOptions
import javax.inject.Inject

/**
 * Implementation of ThemeRepository using DataStore.
 */
class ThemeRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ThemeRepository {

    override fun getThemeOption(): Flow<ThemeOptions> {
        return settingsDataStore.themeOption
    }

    override suspend fun updateThemeOption(themeOption: ThemeOptions) {
        settingsDataStore.updateThemeOption(themeOption)
    }
}

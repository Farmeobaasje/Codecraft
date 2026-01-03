    package nl.codecraft.data.repository

import kotlinx.coroutines.flow.Flow
import nl.codecraft.data.local.SettingsDataStore
import nl.codecraft.domain.repository.ThemeRepository
import nl.codecraft.model.ThemeOptions
import nl.codecraft.model.ThemeStyle
import timber.log.Timber
import javax.inject.Inject

/**
 * Implementation of ThemeRepository using DataStore.
 */
class ThemeRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ThemeRepository {

    init {
        Timber.d("ThemeRepositoryImpl created")
    }

    override fun getThemeOption(): Flow<ThemeOptions> {
        Timber.d("getThemeOption called")
        return settingsDataStore.themeOption
    }

    override fun getThemeStyle(): Flow<ThemeStyle> {
        Timber.d("getThemeStyle called")
        return settingsDataStore.themeStyle
    }

    override suspend fun updateThemeOption(themeOption: ThemeOptions) {
        Timber.d("updateThemeOption: $themeOption")
        settingsDataStore.updateThemeOption(themeOption)
    }

    override suspend fun updateThemeStyle(themeStyle: ThemeStyle) {
        Timber.d("updateThemeStyle: $themeStyle")
        settingsDataStore.updateThemeStyle(themeStyle)
    }

    override fun getDefaultUsername(): Flow<String?> {
        Timber.d("getDefaultUsername called")
        return settingsDataStore.defaultUsername
    }

    override suspend fun updateDefaultUsername(username: String?) {
        Timber.d("updateDefaultUsername: $username")
        settingsDataStore.updateDefaultUsername(username)
    }
}

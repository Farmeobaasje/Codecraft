package nl.codecraft.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.codecraft.model.ThemeOptions
import nl.codecraft.model.ThemeStyle

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * DataStore for managing app settings and preferences.
 */
class SettingsDataStore(private val context: Context) {

    private object PreferencesKeys {
        val THEME_OPTION = stringPreferencesKey("theme_option")
        val THEME_STYLE = stringPreferencesKey("theme_style")
    }

    /**
     * Get the current theme option as a Flow.
     */
    val themeOption: Flow<ThemeOptions> = context.dataStore.data
        .map { preferences ->
            val themeString = preferences[PreferencesKeys.THEME_OPTION] ?: ThemeOptions.SYSTEM.name
            ThemeOptions.valueOf(themeString)
        }

    /**
     * Get the current theme style as a Flow.
     */
    val themeStyle: Flow<ThemeStyle> = context.dataStore.data
        .map { preferences ->
            val styleString = preferences[PreferencesKeys.THEME_STYLE] ?: ThemeStyle.GITHUB.name
            ThemeStyle.valueOf(styleString)
        }

    /**
     * Update the theme option.
     */
    suspend fun updateThemeOption(themeOption: ThemeOptions) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_OPTION] = themeOption.name
        }
    }

    /**
     * Update the theme style.
     */
    suspend fun updateThemeStyle(themeStyle: ThemeStyle) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_STYLE] = themeStyle.name
        }
    }
}

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
        val DEFAULT_USERNAME = stringPreferencesKey("default_username")
        val GITHUB_ACCESS_TOKEN = stringPreferencesKey("github_access_token")
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
     * Get the default GitHub username as a Flow.
     */
    val defaultUsername: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DEFAULT_USERNAME]
        }

    /**
     * Get the GitHub access token as a Flow.
     */
    val githubAccessToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.GITHUB_ACCESS_TOKEN]
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

    /**
     * Update the default GitHub username.
     */
    suspend fun updateDefaultUsername(username: String?) {
        context.dataStore.edit { preferences ->
            if (username != null) {
                preferences[PreferencesKeys.DEFAULT_USERNAME] = username
            } else {
                preferences.remove(PreferencesKeys.DEFAULT_USERNAME)
            }
        }
    }

    /**
     * Update the GitHub access token.
     */
    suspend fun updateGithubAccessToken(token: String?) {
        context.dataStore.edit { preferences ->
            if (token != null) {
                preferences[PreferencesKeys.GITHUB_ACCESS_TOKEN] = token
            } else {
                preferences.remove(PreferencesKeys.GITHUB_ACCESS_TOKEN)
            }
        }
    }
}

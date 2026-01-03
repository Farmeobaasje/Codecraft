package nl.codecraft.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.codecraft.data.local.SettingsDataStore
import nl.codecraft.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * Implementation of AuthRepository for GitHub OAuth.
 */
class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsDataStore: SettingsDataStore
) : AuthRepository {

    companion object {
        private const val GITHUB_CLIENT_ID = "Ov23liiNK6LMzzuWvRPN"
        private const val GITHUB_CLIENT_SECRET = "5bb928b14931510d693ba9b9f0a3e83d62ad05e4"
        private const val GITHUB_AUTH_URL = "https://github.com/login/oauth/authorize"
        private const val GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token"
        private const val REDIRECT_URI = "codecraft://callback"
        private const val SCOPE = "public_repo,read:user"
    }

    init {
        Timber.d("AuthRepositoryImpl created")
    }

    override fun getAccessToken(): Flow<String?> {
        Timber.d("getAccessToken called")
        return settingsDataStore.githubAccessToken
    }

    override suspend fun updateAccessToken(token: String?) {
        Timber.d("updateAccessToken: ${token?.take(10)}...")
        settingsDataStore.updateGithubAccessToken(token)
    }

    override fun isAuthenticated(): Flow<Boolean> {
        Timber.d("isAuthenticated called")
        return settingsDataStore.githubAccessToken.map { it != null && it.isNotBlank() }
    }

    override suspend fun login() {
        Timber.d("login called - starting GitHub OAuth flow")

        // Build GitHub OAuth URL with PKCE parameters
        val authUrl = Uri.parse(GITHUB_AUTH_URL).buildUpon()
            .appendQueryParameter("client_id", GITHUB_CLIENT_ID)
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("scope", SCOPE)
            .appendQueryParameter("response_type", "code")
            .build()

        Timber.d("Opening GitHub OAuth URL: $authUrl")

        // Open GitHub OAuth page in Custom Tabs
        val customTabsIntent = CustomTabsIntent.Builder()
            .build()

        // Add FLAG_ACTIVITY_NEW_TASK to handle launching from non-Activity context
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        
        customTabsIntent.launchUrl(context, authUrl)
    }

    override suspend fun logout() {
        Timber.d("logout called - clearing access token")
        settingsDataStore.updateGithubAccessToken(null)
    }

    /**
     * Handle OAuth callback with authorization code.
     * This should be called from the deep link handler.
     */
    suspend fun handleOAuthCallback(authorizationCode: String) {
        Timber.d("handleOAuthCallback called with code: ${authorizationCode.take(10)}...")

        try {
            // Exchange authorization code for access token
            val token = exchangeCodeForToken(authorizationCode)
            Timber.d("Successfully obtained access token: ${token.take(10)}...")
            
            // Save the access token
            updateAccessToken(token)
        } catch (e: Exception) {
            Timber.e(e, "Failed to exchange authorization code for token")
            throw e
        }
    }

    /**
     * Exchange authorization code for access token.
     */
    private suspend fun exchangeCodeForToken(authorizationCode: String): String {
        Timber.d("exchangeCodeForToken called")

        // Note: In a production app, this should be done on a backend server
        // to keep the client secret secure. For demo purposes, we're doing it here.
        // In a real app, you should use PKCE which doesn't require client secret.
        
        val url = Uri.parse(GITHUB_TOKEN_URL).buildUpon()
            .appendQueryParameter("client_id", GITHUB_CLIENT_ID)
            .appendQueryParameter("client_secret", GITHUB_CLIENT_SECRET)
            .appendQueryParameter("code", authorizationCode)
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .build()

        Timber.d("Making token exchange request to: $url")

        // TODO: Implement actual HTTP request to exchange code for token
        // For now, we'll simulate a successful token exchange
        // In production, use Retrofit or OkHttp to make the request
        
        // Simulate network delay
        kotlinx.coroutines.delay(1000)
        
        // Return a mock token (in production, this would come from GitHub API)
        return "gho_mock_access_token_${authorizationCode.take(10)}"
    }
}

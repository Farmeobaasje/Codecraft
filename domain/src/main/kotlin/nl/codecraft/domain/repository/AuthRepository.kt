package nl.codecraft.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing GitHub authentication.
 */
interface AuthRepository {
    /**
     * Get the GitHub access token as a Flow.
     */
    fun getAccessToken(): Flow<String?>

    /**
     * Update the GitHub access token.
     */
    suspend fun updateAccessToken(token: String?)

    /**
     * Check if user is authenticated.
     */
    fun isAuthenticated(): Flow<Boolean>

    /**
     * Start the GitHub OAuth login flow.
     * Opens a browser for user authentication.
     */
    suspend fun login()

    /**
     * Logout the user by clearing the access token.
     */
    suspend fun logout()
}

package nl.codecraft.domain.usecase

import nl.codecraft.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use case for logging out from GitHub.
 */
class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Logout the user by clearing the access token.
     */
    suspend operator fun invoke() {
        authRepository.logout()
    }
}

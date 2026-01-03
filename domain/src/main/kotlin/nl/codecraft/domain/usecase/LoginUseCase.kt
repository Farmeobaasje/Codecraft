package nl.codecraft.domain.usecase

import nl.codecraft.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use case for logging in with GitHub OAuth.
 */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Start the GitHub OAuth login flow.
     */
    suspend operator fun invoke() {
        authRepository.login()
    }
}

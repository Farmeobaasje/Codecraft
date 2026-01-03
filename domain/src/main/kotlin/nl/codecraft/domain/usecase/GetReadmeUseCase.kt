package nl.codecraft.domain.usecase

import nl.codecraft.domain.repository.GitHubRepository
import javax.inject.Inject

class GetReadmeUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    suspend operator fun invoke(owner: String, repo: String): String? {
        return repository.getReadme(owner, repo)
    }
}

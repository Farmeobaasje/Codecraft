package nl.codecraft.domain.usecase

import kotlinx.coroutines.flow.Flow
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.model.Repo
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    operator fun invoke(username: String): Flow<List<Repo>> {
        return repository.getUserRepos(username)
    }
}

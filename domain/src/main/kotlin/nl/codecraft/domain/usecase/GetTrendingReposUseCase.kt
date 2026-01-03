package nl.codecraft.domain.usecase

import kotlinx.coroutines.flow.Flow
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.model.Repo
import javax.inject.Inject

class GetTrendingReposUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    operator fun invoke(): Flow<List<Repo>> {
        return repository.getTrendingRepos()
    }
}

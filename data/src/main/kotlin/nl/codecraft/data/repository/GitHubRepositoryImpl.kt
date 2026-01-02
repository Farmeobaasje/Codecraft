package nl.codecraft.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.codecraft.data.local.RepoDao
import nl.codecraft.data.mapper.toRepo
import nl.codecraft.data.mapper.toRepoEntity
import nl.codecraft.data.remote.GitHubApiService
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.model.Repo
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val repoDao: RepoDao,
    private val apiService: GitHubApiService,
) : GitHubRepository {

    override fun getUserRepos(username: String): Flow<List<Repo>> {
        return repoDao.getRepos().map { entities ->
            entities.map { it.toRepo() }
        }
    }

    override suspend fun refreshUserRepos(username: String) {
        val repos = apiService.getUserRepos(username)
        val entities = repos.map { it.toRepoEntity() }
        repoDao.clearRepos()
        repoDao.insertRepos(entities)
    }
}

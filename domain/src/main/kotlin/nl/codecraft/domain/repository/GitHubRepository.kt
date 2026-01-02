package nl.codecraft.domain.repository

import kotlinx.coroutines.flow.Flow
import nl.codecraft.model.Repo

interface GitHubRepository {
    fun getUserRepos(username: String): Flow<List<Repo>>
    suspend fun refreshUserRepos(username: String)
}

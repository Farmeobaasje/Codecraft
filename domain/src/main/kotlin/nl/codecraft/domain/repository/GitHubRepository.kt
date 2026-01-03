package nl.codecraft.domain.repository

import kotlinx.coroutines.flow.Flow
import nl.codecraft.model.Repo

interface GitHubRepository {
    fun getUserRepos(username: String): Flow<List<Repo>>
    suspend fun refreshUserRepos(username: String)
    suspend fun getRepoById(repoId: Long): Repo?
    
    // Note functionality
    fun getNoteForRepo(repoId: Long): Flow<String?>
    suspend fun saveNoteForRepo(repoId: Long, content: String)
    suspend fun deleteNoteForRepo(repoId: Long)
    
    // Trending functionality
    fun getTrendingRepos(): Flow<List<Repo>>
    suspend fun refreshTrendingRepos()
}

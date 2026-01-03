package nl.codecraft.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import nl.codecraft.data.local.RepoDao
import nl.codecraft.data.local.RepoNoteDao
import nl.codecraft.data.mapper.toRepo
import nl.codecraft.data.mapper.toRepoEntity
import nl.codecraft.data.remote.GitHubApiService
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.model.Repo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepositoryImpl @Inject constructor(
    private val repoDao: RepoDao,
    private val repoNoteDao: RepoNoteDao,
    private val apiService: GitHubApiService,
) : GitHubRepository {

    private val _trendingRepos = MutableStateFlow<List<Repo>>(emptyList())
    private val trendingRepos: StateFlow<List<Repo>> = _trendingRepos.asStateFlow()

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

    override suspend fun getRepoById(repoId: Long): Repo? {
        return repoDao.getRepoById(repoId)?.toRepo()
    }

    override fun getNoteForRepo(repoId: Long): Flow<String?> {
        return repoNoteDao.getNoteByRepoId(repoId).map { entity ->
            entity?.content
        }
    }

    override suspend fun saveNoteForRepo(repoId: Long, content: String) {
        val now = System.currentTimeMillis()
        val note = nl.codecraft.data.local.RepoNoteEntity(
            repoId = repoId,
            content = content,
            createdAt = now,
            updatedAt = now
        )
        repoNoteDao.insertOrUpdate(note)
    }

    override suspend fun deleteNoteForRepo(repoId: Long) {
        repoNoteDao.deleteNote(repoId)
    }

    // Trending functionality
    override fun getTrendingRepos(): Flow<List<Repo>> {
        return trendingRepos
    }

    override suspend fun refreshTrendingRepos() {
        val response = apiService.searchTrendingRepositories()
        val repos = response.items.map { it.toRepo() }
        _trendingRepos.value = repos
    }
}

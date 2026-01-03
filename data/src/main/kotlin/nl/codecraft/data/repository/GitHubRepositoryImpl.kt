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
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepositoryImpl @Inject constructor(
    private val repoDao: RepoDao,
    private val repoNoteDao: RepoNoteDao,
    private val apiService: GitHubApiService,
) : GitHubRepository {

    init {
        Timber.d("GitHubRepositoryImpl created")
    }

    private val _trendingRepos = MutableStateFlow<List<Repo>>(emptyList())
    private val trendingRepos: StateFlow<List<Repo>> = _trendingRepos.asStateFlow()

    override fun getUserRepos(username: String): Flow<List<Repo>> {
        Timber.d("getUserRepos called for username: $username")
        return repoDao.getReposByUsername(username).map { entities ->
            Timber.d("getUserRepos mapped ${entities.size} entities for username: $username")
            entities.map { it.toRepo() }
        }
    }

    override suspend fun refreshUserRepos(username: String) {
        Timber.d("refreshUserRepos called for username: $username")
        try {
            val repos = apiService.getUserRepos(username)
            Timber.d("refreshUserRepos fetched ${repos.size} repos from API")
            val entities = repos.map { it.toRepoEntity() }
            repoDao.clearReposForUsername(username)
            repoDao.insertRepos(entities)
            Timber.d("refreshUserRepos saved ${entities.size} entities to database for username: $username")
        } catch (e: Exception) {
            Timber.e(e, "refreshUserRepos failed for username: $username")
            throw e
        }
    }

    override suspend fun getRepoById(repoId: Long): Repo? {
        Timber.d("getRepoById called for repoId: $repoId")
        val repo = repoDao.getRepoById(repoId)?.toRepo()
        Timber.d("getRepoById result: ${repo?.name ?: "null"}")
        return repo
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

    override suspend fun getReadme(owner: String, repo: String): String? {
        Timber.d("getReadme called for owner: $owner, repo: $repo")
        return try {
            val readmeDto = apiService.getReadme(owner, repo)
            Timber.d("getReadme fetched README successfully")
            if (readmeDto.encoding == "base64") {
                // Decode base64 content - GitHub's base64 may contain newlines
                val cleanContent = readmeDto.content.replace("\n", "").replace("\r", "")
                val decodedBytes = android.util.Base64.decode(cleanContent, android.util.Base64.DEFAULT)
                String(decodedBytes, Charsets.UTF_8)
            } else {
                readmeDto.content
            }
        } catch (e: retrofit2.HttpException) {
            Timber.w("getReadme HTTP error: ${e.code()} - ${e.message()}")
            // Handle HTTP errors (404, 403, etc.)
            if (e.code() == 404) {
                // README not found
                null
            } else {
                // Other HTTP errors
                null
            }
        } catch (e: Exception) {
            Timber.e(e, "getReadme failed")
            // Return null if README doesn't exist or there's an error
            null
        }
    }
}

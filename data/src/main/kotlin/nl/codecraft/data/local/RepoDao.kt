package nl.codecraft.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {
    @Query("SELECT * FROM repos ORDER BY stargazersCount DESC")
    fun getRepos(): Flow<List<RepoEntity>>

    @Query("SELECT * FROM repos WHERE id = :repoId")
    suspend fun getRepoById(repoId: Long): RepoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)

    @Query("DELETE FROM repos")
    suspend fun clearRepos()
}

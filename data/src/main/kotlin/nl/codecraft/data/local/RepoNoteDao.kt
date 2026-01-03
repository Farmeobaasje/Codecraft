package nl.codecraft.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoNoteDao {
    @Query("SELECT * FROM repo_notes WHERE repoId = :repoId")
    fun getNoteByRepoId(repoId: Long): Flow<RepoNoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(note: RepoNoteEntity)

    @Query("DELETE FROM repo_notes WHERE repoId = :repoId")
    suspend fun deleteNote(repoId: Long)
}

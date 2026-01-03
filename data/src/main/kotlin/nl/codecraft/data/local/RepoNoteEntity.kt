package nl.codecraft.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo_notes")
data class RepoNoteEntity(
    @PrimaryKey
    val repoId: Long,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
)

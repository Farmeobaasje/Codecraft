package nl.codecraft.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.codecraft.model.Owner

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val htmlUrl: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val language: String?,
    val ownerId: Long,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val ownerHtmlUrl: String,
    val ownerType: String,
    val updatedAt: String,
    val isPrivate: Boolean,
    val isFork: Boolean,
)

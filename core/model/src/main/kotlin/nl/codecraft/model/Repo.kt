package nl.codecraft.model

data class Repo(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val htmlUrl: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val language: String?,
    val owner: Owner,
    val updatedAt: String,
    val isPrivate: Boolean,
    val isFork: Boolean,
)

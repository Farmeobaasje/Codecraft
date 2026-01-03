package nl.codecraft.model

data class RepoNote(
    val repoId: Long,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
)

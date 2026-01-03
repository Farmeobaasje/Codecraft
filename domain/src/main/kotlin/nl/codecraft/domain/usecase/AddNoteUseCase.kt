package nl.codecraft.domain.usecase

import nl.codecraft.domain.repository.GitHubRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    suspend operator fun invoke(repoId: Long, content: String) {
        repository.saveNoteForRepo(repoId, content)
    }
}

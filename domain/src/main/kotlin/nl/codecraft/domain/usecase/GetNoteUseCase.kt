package nl.codecraft.domain.usecase

import kotlinx.coroutines.flow.Flow
import nl.codecraft.domain.repository.GitHubRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    operator fun invoke(repoId: Long): Flow<String?> {
        return repository.getNoteForRepo(repoId)
    }
}

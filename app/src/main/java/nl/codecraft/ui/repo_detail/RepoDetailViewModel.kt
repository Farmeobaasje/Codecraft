package nl.codecraft.ui.repo_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.codecraft.domain.usecase.AddNoteUseCase
import nl.codecraft.domain.usecase.GetNoteUseCase
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.model.Repo
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
    private val addNoteUseCase: AddNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val repo: Repo, val note: String? = null) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var currentRepoId: Long? = null

    fun loadRepo(repoId: Long) {
        currentRepoId = repoId
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val repo = gitHubRepository.getRepoById(repoId)
                if (repo != null) {
                    // Start collecting note for this repo
                    getNoteUseCase(repoId).collectLatest { note ->
                        _uiState.value = UiState.Success(repo, note)
                    }
                } else {
                    _uiState.value = UiState.Error("Repository not found")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load repository: ${e.message}")
            }
        }
    }

    fun saveNote(content: String) {
        val repoId = currentRepoId ?: return
        viewModelScope.launch {
            try {
                addNoteUseCase(repoId, content)
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }

    fun deleteNote() {
        val repoId = currentRepoId ?: return
        viewModelScope.launch {
            try {
                gitHubRepository.deleteNoteForRepo(repoId)
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
}

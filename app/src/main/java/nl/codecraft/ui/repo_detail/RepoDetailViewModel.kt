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
import nl.codecraft.domain.usecase.GetReadmeUseCase
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.model.Repo
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
    private val addNoteUseCase: AddNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val getReadmeUseCase: GetReadmeUseCase
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(
            val repo: Repo, 
            val note: String? = null,
            val readme: String? = null
        ) : UiState()
        data class Error(val message: String) : UiState()
    }

    sealed class ReadmeState {
        object Loading : ReadmeState()
        data class Success(val content: String) : ReadmeState()
        data class Error(val message: String) : ReadmeState()
        object Empty : ReadmeState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _readmeState = MutableStateFlow<ReadmeState>(ReadmeState.Empty)
    val readmeState: StateFlow<ReadmeState> = _readmeState.asStateFlow()

    private var currentRepoId: Long? = null
    private var currentRepo: Repo? = null

    fun loadRepo(repoId: Long) {
        currentRepoId = repoId
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val repo = gitHubRepository.getRepoById(repoId)
                if (repo != null) {
                    currentRepo = repo
                    // Start collecting note for this repo
                    getNoteUseCase(repoId).collectLatest { note ->
                        _uiState.value = UiState.Success(repo, note)
                    }
                    // Load README
                    loadReadme(repo.owner.login, repo.name)
                } else {
                    _uiState.value = UiState.Error("Repository not found")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load repository: ${e.message}")
            }
        }
    }

    fun loadReadme(owner: String, repo: String) {
        viewModelScope.launch {
            _readmeState.value = ReadmeState.Loading
            try {
                val readmeContent = getReadmeUseCase(owner, repo)
                if (readmeContent != null && readmeContent.isNotEmpty()) {
                    _readmeState.value = ReadmeState.Success(readmeContent)
                } else {
                    _readmeState.value = ReadmeState.Empty
                }
            } catch (e: Exception) {
                _readmeState.value = ReadmeState.Error("Failed to load README: ${e.message ?: "Unknown error"}")
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

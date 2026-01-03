package nl.codecraft.ui.repo_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.model.Repo
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repository: GitHubRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val username: String = savedStateHandle.get<String>("username") ?: ""
    
    private val _languageFilter = MutableStateFlow<String?>(null)
    val languageFilter: StateFlow<String?> = _languageFilter

    private val _uiState = MutableStateFlow<RepoListUiState>(RepoListUiState.Loading)
    val uiState: StateFlow<RepoListUiState> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        loadRepos()
    }

    fun setLanguageFilter(language: String?) {
        _languageFilter.value = language
        loadRepos()
    }

    fun loadRepos() {
        viewModelScope.launch {
            _uiState.value = RepoListUiState.Loading
            try {
                repository.getUserRepos(username).collectLatest { repos ->
                    // If no repos found in database, try to refresh from API
                    if (repos.isEmpty()) {
                        try {
                            repository.refreshUserRepos(username)
                            // The flow will emit again after refresh, so we don't need to update UI here
                            return@collectLatest
                        } catch (refreshError: Exception) {
                            // Continue with empty list if refresh fails
                        }
                    }
                    
                    val filteredRepos = applyLanguageFilter(repos)
                    if (filteredRepos.isEmpty()) {
                        _uiState.value = RepoListUiState.Empty
                    } else {
                        _uiState.value = RepoListUiState.Success(filteredRepos)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RepoListUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun applyLanguageFilter(repos: List<Repo>): List<Repo> {
        val filter = _languageFilter.value
        return if (filter != null) {
            repos.filter { it.language == filter }
        } else {
            repos
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                repository.refreshUserRepos(username)
            } catch (e: Exception) {
                // Error is handled by the flow
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}

sealed class RepoListUiState {
    object Loading : RepoListUiState()
    object Empty : RepoListUiState()
    data class Success(val repos: List<Repo>) : RepoListUiState()
    data class Error(val message: String) : RepoListUiState()
}

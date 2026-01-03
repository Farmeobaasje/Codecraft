package nl.codecraft.ui.repo_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _uiState = MutableStateFlow<RepoListUiState>(RepoListUiState.Loading)
    val uiState: StateFlow<RepoListUiState> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        loadRepos()
    }

    fun loadRepos() {
        viewModelScope.launch {
            _uiState.value = RepoListUiState.Loading
            try {
                repository.getUserRepos(username).collectLatest { repos ->
                    if (repos.isEmpty()) {
                        _uiState.value = RepoListUiState.Empty
                    } else {
                        _uiState.value = RepoListUiState.Success(repos)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = RepoListUiState.Error(e.message ?: "Unknown error")
            }
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

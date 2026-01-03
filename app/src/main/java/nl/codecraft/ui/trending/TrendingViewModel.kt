package nl.codecraft.ui.trending

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
class TrendingViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TrendingUiState>(TrendingUiState.Loading)
    val uiState: StateFlow<TrendingUiState> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        loadTrendingRepos()
    }

    fun loadTrendingRepos() {
        viewModelScope.launch {
            _uiState.value = TrendingUiState.Loading
            try {
                repository.getTrendingRepos().collectLatest { repos ->
                    if (repos.isEmpty()) {
                        _uiState.value = TrendingUiState.Empty
                    } else {
                        _uiState.value = TrendingUiState.Success(repos)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = TrendingUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                repository.refreshTrendingRepos()
            } catch (e: Exception) {
                // Error is handled by the flow
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}

sealed class TrendingUiState {
    object Loading : TrendingUiState()
    object Empty : TrendingUiState()
    data class Success(val repos: List<Repo>) : TrendingUiState()
    data class Error(val message: String) : TrendingUiState()
}

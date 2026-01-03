package nl.codecraft.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.codecraft.domain.repository.GitHubRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun search(username: String, onSuccess: () -> Unit) {
        if (username.isBlank()) {
            _error.value = "Please enter a GitHub username"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                // First refresh data from API and store in database
                repository.refreshUserRepos(username)
                onSuccess()
            } catch (e: Exception) {
                _error.value = "Failed to fetch repositories: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

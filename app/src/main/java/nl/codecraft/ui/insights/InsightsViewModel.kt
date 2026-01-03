package nl.codecraft.ui.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.domain.repository.ThemeRepository
import nl.codecraft.model.Repo
import javax.inject.Inject

data class LanguageStats(
    val language: String,
    val count: Int,
    val percentage: Float,
    val repos: List<Repo>
)

data class InsightsUiState(
    val languageStats: List<LanguageStats> = emptyList(),
    val selectedLanguage: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val repository: GitHubRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsightsUiState())
    val uiState: StateFlow<InsightsUiState> = _uiState.asStateFlow()

    private var allRepos: List<Repo> = emptyList()

    init {
        loadUserReposWithDefault()
    }

    private fun loadUserReposWithDefault() {
        viewModelScope.launch {
            val defaultUsername = themeRepository.getDefaultUsername().firstOrNull()
            loadUserRepos(defaultUsername ?: "Farmeobaasje")
        }
    }

    fun loadUserRepos(username: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            try {
                repository.refreshUserRepos(username)
                repository.getUserRepos(username).collect { repos ->
                    allRepos = repos
                    calculateLanguageStats(repos)
                    _uiState.update { it.copy(isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Failed to load repositories: ${e.message}"
                    )
                }
            }
        }
    }

    fun selectLanguage(language: String?) {
        _uiState.update { it.copy(selectedLanguage = language) }
    }

    fun clearSelection() {
        _uiState.update { it.copy(selectedLanguage = null) }
    }

    private fun calculateLanguageStats(repos: List<Repo>) {
        val reposWithLanguage = repos.filter { it.language != null }
        val totalWithLanguage = reposWithLanguage.size
        
        if (totalWithLanguage == 0) {
            _uiState.update { it.copy(languageStats = emptyList()) }
            return
        }

        val languageGroups = reposWithLanguage.groupBy { it.language!! }
        val stats = languageGroups.map { (language, languageRepos) ->
            val count = languageRepos.size
            val percentage = (count.toFloat() / totalWithLanguage) * 100
            LanguageStats(
                language = language,
                count = count,
                percentage = percentage,
                repos = languageRepos
            )
        }.sortedByDescending { it.count }

        _uiState.update { it.copy(languageStats = stats) }
    }

    fun getFilteredRepos(): List<Repo> {
        val selectedLanguage = _uiState.value.selectedLanguage
        return if (selectedLanguage != null) {
            allRepos.filter { it.language == selectedLanguage }
        } else {
            allRepos
        }
    }
}

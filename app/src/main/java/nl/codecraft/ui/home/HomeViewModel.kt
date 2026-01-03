package nl.codecraft.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.domain.repository.ThemeRepository
import nl.codecraft.model.Repo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {

    init {
        Timber.d("HomeViewModel created")
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(
            val username: String?,
            val repos: List<Repo>,
            val repoCount: Int = 0,
            val totalStars: Int = 0
        ) : UiState()
        data class Error(val message: String) : UiState()
    }

    data class InsightsState(
        val isLoading: Boolean = false,
        val languages: List<LanguageStat> = emptyList(),
        val languageCount: Int = 0
    )

    data class LanguageStat(
        val name: String,
        val count: Int,
        val percentage: Float,
        val color: androidx.compose.ui.graphics.Color
    )

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _insightsState = MutableStateFlow(InsightsState())
    val insightsState: StateFlow<InsightsState> = _insightsState.asStateFlow()

    private var currentUsername: String? = null

    init {
        loadDefaultUsername()
    }

    private fun loadDefaultUsername() {
        viewModelScope.launch {
            themeRepository.getDefaultUsername().collect { username ->
                Timber.d("loadDefaultUsername: username = $username")
                currentUsername = username
                if (currentUsername != null) {
                    loadUserRepos()
                    loadInsights()
                } else {
                    Timber.w("No default username set")
                    _uiState.value = UiState.Error("No default username set. Please set a username in settings.")
                }
            }
        }
    }

    fun loadUserRepos() {
        val username = currentUsername ?: return
        Timber.d("loadUserRepos called for username: $username")
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                gitHubRepository.getUserRepos(username).collect { repos ->
                    Timber.d("loadUserRepos success: ${repos.size} repos loaded")
                    
                    // If no repos found in database, try to refresh from API
                    if (repos.isEmpty()) {
                        Timber.d("No repos found in database, refreshing from API")
                        try {
                            gitHubRepository.refreshUserRepos(username)
                            // The flow will emit again after refresh, so we don't need to update UI here
                            return@collect
                        } catch (refreshError: Exception) {
                            Timber.e(refreshError, "Failed to refresh repos from API")
                            // Continue with empty list if refresh fails
                        }
                    }
                    
                    val repoCount = repos.size
                    val totalStars = repos.sumBy { it.stargazersCount }
                    
                    _uiState.value = UiState.Success(
                        username = username,
                        repos = repos,
                        repoCount = repoCount,
                        totalStars = totalStars
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "loadUserRepos failed")
                _uiState.value = UiState.Error("Failed to load repositories: ${e.message}")
            }
        }
    }

    fun loadInsights() {
        val username = currentUsername ?: return
        Timber.d("loadInsights called for username: $username")
        viewModelScope.launch {
            _insightsState.value = InsightsState(isLoading = true)
            try {
                gitHubRepository.getUserRepos(username).collect { repos ->
                    Timber.d("loadInsights success: ${repos.size} repos for analysis")
                    val languageStats = calculateLanguageStats(repos)
                    val languageCount = languageStats.size
                    
                    _insightsState.value = InsightsState(
                        isLoading = false,
                        languages = languageStats,
                        languageCount = languageCount
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "loadInsights failed")
                _insightsState.value = InsightsState(
                    isLoading = false,
                    languages = emptyList(),
                    languageCount = 0
                )
            }
        }
    }

    private fun calculateLanguageStats(repos: List<Repo>): List<LanguageStat> {
        val languageMap = mutableMapOf<String, Int>()
        
        repos.forEach { repo ->
            repo.language?.let { language ->
                languageMap[language] = languageMap.getOrDefault(language, 0) + 1
            }
        }
        
        val totalReposWithLanguage = languageMap.values.sum()
        if (totalReposWithLanguage == 0) return emptyList()
        
        return languageMap.entries.map { (language, count) ->
            val percentage = (count.toFloat() / totalReposWithLanguage) * 100
            LanguageStat(
                name = language,
                count = count,
                percentage = percentage,
                color = getLanguageColor(language)
            )
        }.sortedByDescending { it.count }
    }

    private fun getLanguageColor(language: String): androidx.compose.ui.graphics.Color {
        return when (language.lowercase()) {
            "kotlin" -> androidx.compose.ui.graphics.Color(0xFFA97BFF)
            "java" -> androidx.compose.ui.graphics.Color(0xFFB07219)
            "javascript" -> androidx.compose.ui.graphics.Color(0xFFF1E05A)
            "typescript" -> androidx.compose.ui.graphics.Color(0xFF2B7489)
            "python" -> androidx.compose.ui.graphics.Color(0xFF3572A5)
            "go" -> androidx.compose.ui.graphics.Color(0xFF00ADD8)
            "rust" -> androidx.compose.ui.graphics.Color(0xFFDEA584)
            "c++" -> androidx.compose.ui.graphics.Color(0xFFF34B7D)
            "c#" -> androidx.compose.ui.graphics.Color(0xFF178600)
            "swift" -> androidx.compose.ui.graphics.Color(0xFFFFAC45)
            "dart" -> androidx.compose.ui.graphics.Color(0xFF00B4AB)
            else -> androidx.compose.ui.graphics.Color(0xFF7C3AED) // Default purple
        }
    }
}

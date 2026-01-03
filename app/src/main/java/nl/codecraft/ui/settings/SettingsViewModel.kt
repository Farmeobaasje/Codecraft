package nl.codecraft.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.codecraft.domain.repository.AuthRepository
import nl.codecraft.domain.repository.ThemeRepository
import nl.codecraft.domain.usecase.LoginUseCase
import nl.codecraft.domain.usecase.LogoutUseCase
import nl.codecraft.model.ThemeOptions
import nl.codecraft.model.ThemeStyle
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val authRepository: AuthRepository,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val themeOption = themeRepository.getThemeOption()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeOptions.SYSTEM
        )

    val themeStyle = themeRepository.getThemeStyle()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeStyle.GITHUB
        )

    val defaultUsername = themeRepository.getDefaultUsername()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val isAuthenticated = authRepository.isAuthenticated()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun updateThemeOption(themeOption: ThemeOptions) {
        viewModelScope.launch {
            themeRepository.updateThemeOption(themeOption)
        }
    }

    fun updateThemeStyle(themeStyle: ThemeStyle) {
        viewModelScope.launch {
            themeRepository.updateThemeStyle(themeStyle)
        }
    }

    fun updateDefaultUsername(username: String?) {
        viewModelScope.launch {
            themeRepository.updateDefaultUsername(username)
        }
    }

    fun login() {
        viewModelScope.launch {
            loginUseCase()
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}

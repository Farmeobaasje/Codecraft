package nl.codecraft.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.codecraft.domain.repository.ThemeRepository
import nl.codecraft.model.ThemeOptions
import nl.codecraft.model.ThemeStyle
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
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
}

package nl.codecraft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import nl.codecraft.domain.repository.ThemeRepository
import nl.codecraft.model.ThemeOptions
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : ViewModel() {

    val themeOption = themeRepository.getThemeOption()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeOptions.SYSTEM
        )
}

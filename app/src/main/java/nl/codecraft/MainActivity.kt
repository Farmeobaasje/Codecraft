package nl.codecraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import nl.codecraft.model.ThemeOptions
import nl.codecraft.model.ThemeStyle
import nl.codecraft.ui.MainScreen
import nl.codecraft.ui.theme.CodeCraftTheme
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("MainActivity.onCreate() called")
        
        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        // Hide status bar and navigation bar for full-screen experience
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(androidx.core.view.WindowInsetsCompat.Type.systemBars())
        
        setContent {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    val viewModel: MainActivityViewModel = hiltViewModel()
    val themeOption by viewModel.themeOption.collectAsState(initial = ThemeOptions.SYSTEM)
    val themeStyle by viewModel.themeStyle.collectAsState(initial = ThemeStyle.GITHUB)
    
    CodeCraftTheme(themeOption = themeOption, themeStyle = themeStyle) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen()
        }
    }
}

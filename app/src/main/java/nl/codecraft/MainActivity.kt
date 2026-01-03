package nl.codecraft

import android.content.Intent
import android.net.Uri
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.codecraft.data.repository.AuthRepositoryImpl
import nl.codecraft.model.ThemeOptions
import nl.codecraft.model.ThemeStyle
import nl.codecraft.ui.MainScreen
import nl.codecraft.ui.theme.CodeCraftTheme
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var authRepository: AuthRepositoryImpl
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("MainActivity.onCreate() called")
        
        // Handle deep link if app was launched from GitHub OAuth callback
        handleDeepLink(intent)
        
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
    
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Timber.d("MainActivity.onNewIntent() called")
        
        // Handle deep link if app was already running
        handleDeepLink(intent)
    }
    
    private fun handleDeepLink(intent: Intent?) {
        Timber.d("handleDeepLink called with intent: $intent")
        
        val data: Uri? = intent?.data
        if (data != null) {
            Timber.d("Deep link data: $data")
            
            // Check if this is a GitHub OAuth callback
            if (data.scheme == "codecraft" && data.host == "callback") {
                Timber.d("GitHub OAuth callback detected")
                
                // Extract authorization code from query parameters
                val authorizationCode = data.getQueryParameter("code")
                if (authorizationCode != null) {
                    Timber.d("Authorization code found: ${authorizationCode.take(10)}...")
                    
                    // Handle OAuth callback in background
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            authRepository.handleOAuthCallback(authorizationCode)
                            Timber.d("OAuth callback handled successfully")
                        } catch (e: Exception) {
                            Timber.e(e, "Failed to handle OAuth callback")
                        }
                    }
                } else {
                    Timber.w("No authorization code found in callback URL")
                }
            }
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

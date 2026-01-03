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
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import nl.codecraft.ui.navigation.CodeCraftNavigation
import nl.codecraft.ui.theme.CodeCraftTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    val viewModel: MainActivityViewModel = hiltViewModel()
    val themeOption by viewModel.themeOption.collectAsState(initial = nl.codecraft.model.ThemeOptions.SYSTEM)
    
    CodeCraftTheme(themeOption = themeOption) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CodeCraftNavigation()
        }
    }
}

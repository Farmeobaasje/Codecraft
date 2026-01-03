package nl.codecraft.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.codecraft.R
import nl.codecraft.model.ThemeOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeOption by viewModel.themeOption.collectAsState(initial = ThemeOptions.SYSTEM)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Appearance Section
            Text(
                text = stringResource(R.string.appearance_section_title),
                style = MaterialTheme.typography.titleMedium
            )

            ThemeOptions.values().forEach { option ->
                ThemeOptionItem(
                    option = option,
                    isSelected = themeOption == option,
                    onOptionSelected = { viewModel.updateThemeOption(option) }
                )
            }

            // Data & Storage Section
            Text(
                text = stringResource(R.string.data_storage_section_title),
                style = MaterialTheme.typography.titleMedium
            )

            // About Section
            Text(
                text = stringResource(R.string.about_section_title),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun ThemeOptionItem(
    option: ThemeOptions,
    isSelected: Boolean,
    onOptionSelected: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onOptionSelected
        )
        Text(
            text = when (option) {
                ThemeOptions.LIGHT -> stringResource(R.string.theme_light)
                ThemeOptions.DARK -> stringResource(R.string.theme_dark)
                ThemeOptions.SYSTEM -> stringResource(R.string.theme_system)
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 48.dp)
        )
    }
}

package nl.codecraft.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.codecraft.R
import nl.codecraft.model.ThemeOptions
import nl.codecraft.model.ThemeStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeOption by viewModel.themeOption.collectAsState(initial = ThemeOptions.SYSTEM)
    val themeStyle by viewModel.themeStyle.collectAsState(initial = ThemeStyle.GITHUB)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Back button row
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateUp,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.titleLarge
            )
        }

        // Appearance Section
        Text(
            text = stringResource(R.string.appearance_section_title),
            style = MaterialTheme.typography.titleMedium
        )

        // Theme Style Selection
        Text(
            text = "Theme Style",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 8.dp)
        )

        ThemeStyleDropdown(
            selectedStyle = themeStyle,
            onStyleSelected = { viewModel.updateThemeStyle(it) }
        )

        // Theme Mode Selection
        Text(
            text = "Theme Mode",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 16.dp)
        )

        ThemeModeDropdown(
            selectedOption = themeOption,
            onOptionSelected = { viewModel.updateThemeOption(it) }
        )

        // Data & Storage Section
        Text(
            text = stringResource(R.string.data_storage_section_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 24.dp)
        )

        // About Section
        Text(
            text = stringResource(R.string.about_section_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 24.dp)
        )
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

@Composable
private fun ThemeStyleItem(
    style: ThemeStyle,
    isSelected: Boolean,
    onStyleSelected: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onStyleSelected
        )
        Text(
            text = when (style) {
                ThemeStyle.GITHUB -> stringResource(R.string.theme_style_github)
                ThemeStyle.CODECRAFT_PREMIUM -> stringResource(R.string.theme_style_codecraft)
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 48.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeStyleDropdown(
    selectedStyle: ThemeStyle,
    onStyleSelected: (ThemeStyle) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = when (selectedStyle) {
                ThemeStyle.GITHUB -> stringResource(R.string.theme_style_github)
                ThemeStyle.CODECRAFT_PREMIUM -> stringResource(R.string.theme_style_codecraft)
            },
            onValueChange = {},
            label = { Text("Select theme style") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ThemeStyle.values().forEach { style ->
                DropdownMenuItem(
                    text = { 
                        Text(when (style) {
                            ThemeStyle.GITHUB -> stringResource(R.string.theme_style_github)
                            ThemeStyle.CODECRAFT_PREMIUM -> stringResource(R.string.theme_style_codecraft)
                        })
                    },
                    onClick = {
                        onStyleSelected(style)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeModeDropdown(
    selectedOption: ThemeOptions,
    onOptionSelected: (ThemeOptions) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = when (selectedOption) {
                ThemeOptions.LIGHT -> stringResource(R.string.theme_light)
                ThemeOptions.DARK -> stringResource(R.string.theme_dark)
                ThemeOptions.SYSTEM -> stringResource(R.string.theme_system)
            },
            onValueChange = {},
            label = { Text("Select theme mode") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ThemeOptions.values().forEach { option ->
                DropdownMenuItem(
                    text = { 
                        Text(when (option) {
                            ThemeOptions.LIGHT -> stringResource(R.string.theme_light)
                            ThemeOptions.DARK -> stringResource(R.string.theme_dark)
                            ThemeOptions.SYSTEM -> stringResource(R.string.theme_system)
                        })
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

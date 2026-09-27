package ru.netology.weatherapp.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.netology.weatherapp.R

data class CityOption(val alias: String, val nameRes: Int)

val availableCities = listOf(
    CityOption("moscow", R.string.city_moscow),
    CityOption("novosibirsk", R.string.city_novosibirsk),
    CityOption("spb", R.string.city_saint_petersburg),
    CityOption("ekaterinburg", R.string.city_ekaterinburg),
    CityOption("kazan", R.string.city_kazan),
    CityOption("krasnoyarsk", R.string.city_krasnoyarsk)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val settings = uiState.settings

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
            // Выбор города
            SettingsSection(title = stringResource(R.string.settings_city)) {
                var expanded by remember { mutableStateOf(false) }
                val selectedCity = availableCities.find { it.alias == settings.city }
                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectedCity?.let { stringResource(it.nameRes) } ?: settings.city,
                            modifier = Modifier.weight(1f)
                        )
                        Text("▼")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        availableCities.forEach { city ->
                            DropdownMenuItem(
                                text = { Text(stringResource(city.nameRes)) },
                                onClick = {
                                    viewModel.setCity(city.alias)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Прогнозируемые дни
            SettingsSection(title = stringResource(R.string.settings_days)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${settings.forecastDays} ${stringResource(R.string.days_suffix)}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            if (settings.forecastDays > 1) viewModel.setForecastDays(settings.forecastDays - 1)
                        }
                    ) {
                        Text("-", fontSize = 24.sp)
                    }
                    Text(
                        text = settings.forecastDays.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(
                        onClick = {
                            if (settings.forecastDays < 14) viewModel.setForecastDays(settings.forecastDays + 1)
                        }
                    ) {
                        Text("+", fontSize = 24.sp)
                    }
                }
            }

            // Выбор темы
            SettingsSection(title = stringResource(R.string.settings_theme)) {
                ThemeSelector(
                    selected = settings.themeMode,
                    onSelected = { mode ->
                        viewModel.setThemeMode(mode)
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            content()
        }
    }
}

@Composable
private fun ThemeSelector(
    selected: String,
    onSelected: (String) -> Unit
) {
    val options = listOf(
        "light" to stringResource(R.string.theme_light),
        "dark" to stringResource(R.string.theme_dark)
    )
    Column {
        options.forEach { (value, label) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelected(value) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selected == value,
                    onClick = { onSelected(value) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(label, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

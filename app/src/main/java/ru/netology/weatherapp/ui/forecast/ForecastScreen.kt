package ru.netology.weatherapp.ui.forecast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.netology.weatherapp.R
import ru.netology.weatherapp.data.local.WeatherEntity
import ru.netology.weatherapp.util.WeatherUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedDay by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.forecast_title)) },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = stringResource(R.string.settings_title))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                uiState.error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.error_loading),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadForecast() }) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }

                uiState.forecasts.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.no_data),
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (uiState.isFromCache) {
                            item {
                                OfflineBanner()
                            }
                        }
                        items(uiState.forecasts) { entity ->
                            val index = uiState.forecasts.indexOf(entity)
                            ForecastDayCard(
                                entity = entity,
                                isExpanded = selectedDay == index,
                                onClick = {
                                    selectedDay = if (selectedDay == index) null else index
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OfflineBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.WifiOff,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.offline_data),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
private fun ForecastDayCard(
    entity: WeatherEntity,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            // Итоговая строка
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // День
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = WeatherUtils.formatDate(entity.date),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = WeatherUtils.getWeatherCondition(entity),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Температура
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = WeatherUtils.formatTemp(entity.tempMax),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = WeatherUtils.formatTemp(entity.tempMin),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Осадки
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.WaterDrop,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${entity.precipitationProbability.toInt()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Детализированный прогноз
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                DetailedForecastContent(entity)
            }
        }
    }
}

@Composable
private fun DetailedForecastContent(entity: WeatherEntity) {
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DetailRow(
            icon = Icons.Default.Thermostat,
            label = stringResource(R.string.temperature),
            value = "${WeatherUtils.formatTempValue(entity.tempAvg)}${stringResource(R.string.celsius_symbol)}"
        )
        DetailRow(
            icon = Icons.Default.ArrowUpward,
            label = stringResource(R.string.temp_max),
            value = "${WeatherUtils.formatTempValue(entity.tempMax)}${stringResource(R.string.celsius_symbol)}"
        )
        DetailRow(
            icon = Icons.Default.ArrowDownward,
            label = stringResource(R.string.temp_min),
            value = "${WeatherUtils.formatTempValue(entity.tempMin)}${stringResource(R.string.celsius_symbol)}"
        )
        DetailRow(
            icon = Icons.Default.WaterDrop,
            label = stringResource(R.string.humidity),
            value = "${entity.humidity.toInt()}${stringResource(R.string.percent_symbol)}"
        )
        DetailRow(
            icon = Icons.Default.Speed,
            label = stringResource(R.string.pressure),
            value = "${entity.pressure.toInt()} ${stringResource(R.string.mmhg_symbol)}"
        )
        DetailRow(
            icon = Icons.Default.Air,
            label = stringResource(R.string.wind),
            value = "${entity.windSpeed.toInt()} ${stringResource(R.string.ms_symbol)} ${WeatherUtils.windDirectionToCompass(entity.windDirection)}"
        )
        DetailRow(
            icon = Icons.Default.Umbrella,
            label = stringResource(R.string.precipitation_probability),
            value = "${entity.precipitationProbability.toInt()}${stringResource(R.string.percent_symbol)}"
        )
    }
}

@Composable
private fun DetailRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

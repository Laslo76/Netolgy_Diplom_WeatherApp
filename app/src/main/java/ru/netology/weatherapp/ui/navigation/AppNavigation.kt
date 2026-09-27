package ru.netology.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.netology.weatherapp.ui.forecast.ForecastScreen
import ru.netology.weatherapp.ui.settings.SettingsScreen

object Routes {
    const val FORECAST = "forecast"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.FORECAST) {
        composable(Routes.FORECAST) {
            ForecastScreen(
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

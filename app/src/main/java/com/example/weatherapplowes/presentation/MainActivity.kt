package com.example.weatherapplowes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapplowes.presentation.detail.WeatherInfoDetailScreen
import com.example.weatherapplowes.presentation.forecast.WeatherListScreen
import com.example.weatherapplowes.presentation.search.SearchScreen
import com.example.weatherapplowes.presentation.theme.WeatherAppLowesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ForecastViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppLowesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SearchScreen.route
                    ) {
                        composable(
                            route = Screen.SearchScreen.route
                        ) {
                            SearchScreen(navController, viewModel)
                        }
                        composable(
                            route = Screen.WeatherInfoListScreen.route
                        ) {
                            WeatherListScreen(navController, viewModel)
                        }
                        composable(
                            route = Screen.WeatherInfoDetailScreen.route
                        ) {
                            WeatherInfoDetailScreen(navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}
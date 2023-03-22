package com.example.weatherapplowes.presentation

sealed class Screen(val route: String) {
    object SearchScreen: Screen("search_screen")
    object WeatherInfoListScreen: Screen("weather_info_list_screen")
    object WeatherInfoDetailScreen: Screen("weather_info_detail")
}

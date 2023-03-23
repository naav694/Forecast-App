package com.example.weatherapplowes.domain.model

data class UiForecastState(
    val weatherInfoList: List<WeatherInfo> = emptyList(),
    val city: String = ""
)

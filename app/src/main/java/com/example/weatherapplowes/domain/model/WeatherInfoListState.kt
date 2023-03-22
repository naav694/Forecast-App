package com.example.weatherapplowes.domain.model

data class WeatherInfoListState(
    val weatherInfoList: List<WeatherInfo> = emptyList(),
    val city: String = ""
)

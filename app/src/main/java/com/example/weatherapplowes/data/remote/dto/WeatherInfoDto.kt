package com.example.weatherapplowes.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfoDto(
    val dt: Long,
    val main: TemperatureInfoDto,
    val weather: List<WeatherDto>?
)




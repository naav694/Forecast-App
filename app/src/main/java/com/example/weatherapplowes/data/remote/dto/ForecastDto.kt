package com.example.weatherapplowes.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastDto(
    val cod: String,
    val message: String?,
    val city: CityDto,
    val list: List<WeatherInfoDto>
)

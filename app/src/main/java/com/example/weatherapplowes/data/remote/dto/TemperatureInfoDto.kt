package com.example.weatherapplowes.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TemperatureInfoDto(
    val temp: String,
    @Json(name = "feels_like")
    val feelsLike: String
)

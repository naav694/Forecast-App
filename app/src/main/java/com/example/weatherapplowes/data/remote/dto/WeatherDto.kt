package com.example.weatherapplowes.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDto(
    val main: String?,
    val description: String?
)

package com.example.weatherapplowes.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityDto(
    val name: String,
    val country: String
)

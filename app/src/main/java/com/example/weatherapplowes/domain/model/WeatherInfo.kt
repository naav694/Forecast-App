package com.example.weatherapplowes.domain.model

import com.example.weatherapplowes.data.remote.dto.WeatherInfoDto

data class WeatherInfo(
    val dt: Long,
    val info: String?,
    val temp: String,
    val weatherDetail: WeatherDetail
)

fun List<WeatherInfoDto>.toWeatherInfoList() = map {
    WeatherInfo(
        dt = it.dt,
        info = it.weather?.firstOrNull()?.main ?: "",
        temp = it.main.temp,
        WeatherDetail(
            temp = it.main.temp,
            feelsLike = it.main.feelsLike,
            condition = it.weather?.firstOrNull()?.main ?: "",
            details = it.weather?.firstOrNull()?.description ?: ""
        )
    )
}

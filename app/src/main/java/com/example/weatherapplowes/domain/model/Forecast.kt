package com.example.weatherapplowes.domain.model


import com.example.weatherapplowes.data.remote.dto.ForecastDto

data class Forecast(
    val weatherInfoList: List<WeatherInfo>,
    val city: String
)

fun ForecastDto.toForecast() = Forecast(
    weatherInfoList = list.toWeatherInfoList(),
    city = city.name
)



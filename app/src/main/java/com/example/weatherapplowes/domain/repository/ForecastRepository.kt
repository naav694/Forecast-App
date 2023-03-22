package com.example.weatherapplowes.domain.repository

import com.example.weatherapplowes.common.Result
import com.example.weatherapplowes.domain.model.Forecast

interface ForecastRepository {
    suspend fun getForecastByCity(
        city: String,
        units: String
    ): Result<Forecast>
}
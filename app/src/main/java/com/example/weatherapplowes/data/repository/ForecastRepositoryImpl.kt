package com.example.weatherapplowes.data.repository

import com.example.weatherapplowes.common.Result
import com.example.weatherapplowes.data.remote.ForecastApi
import com.example.weatherapplowes.domain.model.Forecast
import com.example.weatherapplowes.domain.model.toForecast
import com.example.weatherapplowes.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val api: ForecastApi
) : ForecastRepository {
    override suspend fun getForecastByCity(
        city: String,
        units: String
    ): Result<Forecast> {
        val response = api.getForecast(city, units)
        return if (response.isSuccessful) {
            response.body()?.let {
                when (it.cod) {
                    "200" -> Result.Success(it.toForecast())
                    else -> Result.Error(Exception())
                }
            } ?: Result.Message("Unknown Error")
        } else {
            if (response.code() == 404) {
                return Result.Message("City Not Found")
            }
            Result.Error(Exception())
        }
    }
}
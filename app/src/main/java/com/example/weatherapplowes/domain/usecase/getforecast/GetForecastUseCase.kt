package com.example.weatherapplowes.domain.usecase.getforecast

import com.example.weatherapplowes.common.Result
import com.example.weatherapplowes.domain.model.Forecast
import com.example.weatherapplowes.domain.repository.ForecastRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {
    suspend operator fun invoke(city: String, units: String): Result<Forecast> {
        return repository.getForecastByCity(
            city = city,
            units = units
        )
    }
}
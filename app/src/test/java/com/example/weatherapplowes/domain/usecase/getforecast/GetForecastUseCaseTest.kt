package com.example.weatherapplowes.domain.usecase.getforecast

import com.example.weatherapplowes.common.Constants
import com.example.weatherapplowes.common.Result
import com.example.weatherapplowes.domain.model.Forecast
import com.example.weatherapplowes.domain.model.WeatherDetail
import com.example.weatherapplowes.domain.model.WeatherInfo
import com.example.weatherapplowes.domain.repository.ForecastRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetForecastUseCaseTest {

    private lateinit var mockForecastRepository: ForecastRepository
    private lateinit var getForecastUseCase: GetForecastUseCase

    private val forecastResponse = Forecast(
        city = "Atlanta",
        weatherInfoList = listOf(
            WeatherInfo(
                dt = 1L,
                info = "Clouds",
                temp = "50.77",
                WeatherDetail(
                    temp = "50.77",
                    feelsLike = "47.57",
                    condition = "Clouds",
                    details = "broken clouds"
                )
            ),
            WeatherInfo(
                dt = 2L,
                info = "Clouds",
                temp = "67.18",
                WeatherDetail(
                    temp = "67.18",
                    feelsLike = "66.42",
                    condition = "Clouds",
                    details = "overcast clouds"
                )
            ),
            WeatherInfo(
                dt = 3L,
                info = "Clear",
                temp = "55.72",
                WeatherDetail(
                    temp = "55.72",
                    feelsLike = "54.95",
                    condition = "Clear",
                    details = "clear sky"
                )
            )
        )
    )

    @Before
    fun setup() {
        mockForecastRepository = Mockito.mock(ForecastRepository::class.java)
        getForecastUseCase = GetForecastUseCase(mockForecastRepository)
    }


    @Test
    fun `Get Forecast On Success`() = runTest {
        `when`(getForecastUseCase("Atlanta", Constants.UNITS))
                .thenReturn(Result.Success(forecastResponse))

        val response = getForecastUseCase("Atlanta", Constants.UNITS)
        response as Result.Success

        assertThat(response.data, IsEqual(forecastResponse))
    }

    @Test
    fun `Get Forecast On Error`() = runTest {
        `when`(getForecastUseCase("Atlanta", Constants.UNITS))
            .thenReturn(Result.Error(Exception()))

        val response = getForecastUseCase("Atlanta", Constants.UNITS)
        response as Result.Error

        assertThat(response, IsInstanceOf.instanceOf(Result.Error::class.java))
    }

}
package com.example.weatherapplowes.data.repository

import com.example.weatherapplowes.common.*
import com.example.weatherapplowes.data.remote.ForecastApi
import com.example.weatherapplowes.data.remote.dto.*
import com.example.weatherapplowes.domain.model.Forecast
import com.example.weatherapplowes.domain.model.WeatherDetail
import com.example.weatherapplowes.domain.model.WeatherInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ForecastRepositoryImplTest {

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

    private var forecastDtoResponse = ForecastDto(
        cod = "200",
        message = "0",
        city = CityDto("Atlanta", "US"),
        list = listOf(
            WeatherInfoDto(
                dt = 1L,
                main = TemperatureInfoDto(temp = "50.77", feelsLike = "47.57"),
                weather = listOf(
                    WeatherDto(main = "Clouds", description = "broken clouds")
                )
            ),
            WeatherInfoDto(
                dt = 2L,
                main = TemperatureInfoDto(temp = "67.18", feelsLike = "66.42"),
                weather = listOf(
                    WeatherDto(main = "Clouds", description = "overcast clouds")
                )
            ),
            WeatherInfoDto(
                dt = 3L,
                main = TemperatureInfoDto(temp = "55.72", feelsLike = "54.95"),
                weather = listOf(
                    WeatherDto(main = "Clear", description = "clear sky")
                )
            )
        )
    )


    @Test
    fun `Get Forecast Response Success`() = runTest {
        val fakeForecastApi = mock(ForecastApi::class.java)
        `when`(fakeForecastApi.getForecast("Atlanta", Constants.UNITS))
            .thenReturn(Response.success(forecastDtoResponse))

        val forecastRepository = ForecastRepositoryImpl(fakeForecastApi)

        val response = forecastRepository.getForecastByCity("Atlanta", Constants.UNITS)
        response as Result.Success

        assertThat(response.data, IsEqual(forecastResponse))
    }

    @Test
    fun `Get Forecast Response Error`() = runTest {
        val apiResponse = ForecastDto(
            cod = "404",
            message = "City not found",
            city = CityDto("", ""),
            emptyList()
        )
        val fakeForecastApi = mock(ForecastApi::class.java)
        `when`(fakeForecastApi.getForecast("Atlanta", Constants.UNITS))
            .thenReturn(Response.success(apiResponse))

        val forecastRepository = ForecastRepositoryImpl(fakeForecastApi)

        val response = forecastRepository.getForecastByCity("Atlanta", Constants.UNITS)
        response as Result.Error

        assertThat(response, instanceOf(Result.Error::class.java))
    }

    @Test
    fun `Get Forecast Response Exception`() = runTest {
        val fakeForecastApi = mock(ForecastApi::class.java)
        given(fakeForecastApi.getForecast("Atlanta", Constants.UNITS))
            .willAnswer {
                throw IOException()
            }

        val forecastRepository = ForecastRepositoryImpl(fakeForecastApi)
        val response = forecastRepository.getForecastByCity("Atlanta", Constants.UNITS)

        assertThat(response, instanceOf(Result.Error::class.java))
    }

}
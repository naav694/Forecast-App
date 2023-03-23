package com.example.weatherapplowes.presentation

import com.example.weatherapplowes.MainCoroutineRule
import com.example.weatherapplowes.common.*
import com.example.weatherapplowes.domain.model.*
import com.example.weatherapplowes.domain.usecase.getforecast.GetForecastUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ForecastViewModelTest {

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

    private lateinit var forecastViewModel: ForecastViewModel

    @Mock
    lateinit var getForecastUseCase: GetForecastUseCase

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        forecastViewModel = ForecastViewModel(getForecastUseCase)
    }

    @Test
    fun `Get the Forecast Is Success`() = runTest {
        `when`(getForecastUseCase.invoke("Atlanta", Constants.UNITS))
            .thenReturn(Result.Success(forecastResponse))

        forecastViewModel.getWeatherInfoList("Atlanta")

        forecastViewModel.uiSearchState.value.run {
            assertThat(isLoading, equalTo(false))
        }

        forecastViewModel.uiForecastState.value.run {
            assertThat(weatherInfoList, equalTo(forecastResponse.weatherInfoList))
            assertThat(city, equalTo(forecastResponse.city))
        }
    }

    @Test
    fun `Get the Forest Is Error`() = runTest {
        `when`(getForecastUseCase.invoke("Atlanta", Constants.UNITS))
            .thenReturn(Result.Error(Exception()))

        forecastViewModel.getWeatherInfoList("Atlanta")

        forecastViewModel.uiSearchState.value.run {
            assertThat(error, equalTo("Unknown Error"))
        }

    }

    @Test
    fun `Get WeatherDetail`() = runTest {
        `when`(getForecastUseCase.invoke("Atlanta", Constants.UNITS))
            .thenReturn(Result.Success(forecastResponse))

        forecastViewModel.getWeatherInfoList("Atlanta")

        forecastViewModel.uiForecastState.value.run {
            val weatherDetail = weatherInfoList.first().weatherDetail
            forecastViewModel.detailState.value.run {
                assertThat(weatherDetail, equalTo(forecastResponse.weatherInfoList.first().weatherDetail))
            }
        }
    }


}
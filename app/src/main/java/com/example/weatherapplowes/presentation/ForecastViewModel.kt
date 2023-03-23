package com.example.weatherapplowes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplowes.common.Constants
import com.example.weatherapplowes.common.Result
import com.example.weatherapplowes.domain.model.*
import com.example.weatherapplowes.domain.usecase.getforecast.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {
    private val _uiSearchState = MutableStateFlow(SearchState())
    val uiSearchState: StateFlow<SearchState> = _uiSearchState

    private val _uiForecastState = MutableStateFlow(UiForecastState())
    val uiForecastState: StateFlow<UiForecastState> = _uiForecastState

    private val _detailState = MutableStateFlow(WeatherDetail())
    val detailState: StateFlow<WeatherDetail> = _detailState

    private val _navigationListScreenEvent = MutableSharedFlow<NavigationListScreenEvent>()
    val navigationListScreenEvent = _navigationListScreenEvent.asSharedFlow()

    private val _navigationSearchScreenEvent = MutableSharedFlow<NavigationSearchScreenEvent>()
    val navigationSearchScreenEvent = _navigationSearchScreenEvent.asSharedFlow()

    fun resetErrorMessage() {
        _uiSearchState.update {
            it.copy(error = "")
        }
    }

    fun getWeatherInfoDetail(dt: Long) {
        viewModelScope.launch {
            val weatherInfo = _uiForecastState.value.weatherInfoList.firstOrNull {
                it.dt == dt
            }
            weatherInfo?.let { _detailState.value = it.weatherDetail }
            _navigationListScreenEvent.emit(NavigationListScreenEvent.NavigateToDetail)
        }
    }

    fun getWeatherInfoList(city: String) {
        _uiSearchState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            getForecastUseCase(
                city,
                Constants.UNITS
            ).let { result ->
                _uiSearchState.update {
                    it.copy(isLoading = false)
                }
                when (result) {
                    is Result.Error -> {
                        _uiSearchState.update {
                            it.copy(error = result.exception.message ?: "Unknown Error")
                        }
                    }
                    is Result.Message -> {
                        _uiSearchState.update {
                            it.copy(error = result.message)
                        }
                    }
                    is Result.Success -> {
                        _uiForecastState.update {
                            it.copy(
                                weatherInfoList = result.data.weatherInfoList,
                                city = city
                            )
                        }
                        _navigationSearchScreenEvent.emit(NavigationSearchScreenEvent.NavigateToListScreen)
                    }
                }
            }
        }
    }
}
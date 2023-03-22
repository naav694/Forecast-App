package com.example.weatherapplowes.data.remote

import com.example.weatherapplowes.BuildConfig
import com.example.weatherapplowes.common.Constants
import com.example.weatherapplowes.data.remote.dto.ForecastDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET(Constants.FORECAST)
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ) : Response<ForecastDto>

}
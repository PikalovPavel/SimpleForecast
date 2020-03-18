package com.example.simpleforecast.Data.Remote

import com.example.simpleforecast.Data.Remote.Response.CityResponse
import com.example.simpleforecast.Data.Remote.WeatherResponse.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ForecastApi {
    @GET("vesti.rss")
    fun getCity(): Single<CityResponse>
    @GET("currentconditions/v1/{id}")
    fun getWeather(@Path("id") locationId:String): Single<List<WeatherResponse>>
}
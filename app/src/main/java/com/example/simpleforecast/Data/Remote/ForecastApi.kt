package com.example.simpleforecast.Data.Remote

import com.example.simpleforecast.Data.Remote.CityResponse.CityResponse
import com.example.simpleforecast.Data.Remote.WeatherResponse.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ForecastApi {
    @GET("locations/v1/cities/search")
    fun getCities(@Query("q") city:String): Single<List<CityResponse>>
    @GET("currentconditions/v1/{id}")
    fun getWeather(@Path("id") locationId:String): Single<List<WeatherResponse>>
}
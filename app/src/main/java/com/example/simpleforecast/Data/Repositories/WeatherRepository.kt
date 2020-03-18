package com.example.simpleforecast.Data.Repositories

import android.util.Log
import com.example.simpleforecast.Data.Local.WeatherItem
import com.example.simpleforecast.Data.Remote.ForecastApi
import com.example.simpleforecast.Data.Remote.WeatherResponse.WeatherResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

class WeatherRepository (private val remote: ForecastApi) {


    private val REMOTE = "REMOTE_ERROR"

    fun getWeather(list: List<String>): Flowable<WeatherItem> {
        val obserVList = mutableListOf<Single<WeatherItem>>()
        list.forEach { locationId ->
            obserVList.add(remote.getWeather(locationId)
                .map {it.first()}
                .map{WeatherItem(temperature = it.temperature.metric.value.toString(),
                    temperatureDescription = it.weatherText, icon = it.weatherIcon, cityId = locationId)})
        }
        return Single.merge(obserVList)
    }


}
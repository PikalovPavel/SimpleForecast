package com.example.simpleforecast.Data.Repositories

import com.example.simpleforecast.Data.Local.Database.Dao.WeatherDao
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.Data.Local.Database.Entity.Weather
import com.example.simpleforecast.Data.Local.Util.CityTemperature
import com.example.simpleforecast.Data.Remote.CityResponse.CityResponse
import com.example.simpleforecast.Data.Remote.ForecastApi
import com.example.simpleforecast.Data.Remote.WeatherResponse.WeatherResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.intellij.lang.annotations.Flow

class WeatherRepository (private val remote: ForecastApi, private val local: WeatherDao) {


    private val REMOTE = "REMOTE_ERROR"

    fun getCites(city: String):Single<List<City>> {
             return remote.getCities(city)
                .subscribeOn(Schedulers.newThread())
                 .flatMap { list ->
                     Observable.fromIterable(list).flatMap { cityResponse ->
                        remote.getWeather(cityResponse.key, "true")
                            .map{ weatherResponse ->
                                weatherResponse.first().mapToLocal(cityResponse.key)
                            }
                            .toObservable()
                            .doOnNext { local.addWeatherInCity(
                                    cityResponse.mapToLocal(it.temperature), it)
                            }.map {cityResponse.mapToLocal(it.temperature)}
                    }.toList()
                 }.onErrorResumeNext{
                     local.getCities(city)
                 }
         }

    fun getWeather(cityId: String): Single<Weather> {
        return local.getWeather(cityId)
    }

}




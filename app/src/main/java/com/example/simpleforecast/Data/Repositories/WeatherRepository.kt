package com.example.simpleforecast.Data.Repositories

import android.util.Log
import com.example.simpleforecast.Data.Local.Database.Dao.WeatherDao
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.Data.Local.Database.Entity.CityWeather
import com.example.simpleforecast.Data.Local.Database.Entity.Weather
import com.example.simpleforecast.Data.Remote.ForecastApi
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
private const val REMOTE = "REMOTE_ERROR"

class WeatherRepository (private val remote: ForecastApi, private val local: WeatherDao) {



    fun getCites(city: String):Single<List<City>> {
        Log.d(REMOTE+0, city)
             return remote.getCities(city)
                .subscribeOn(Schedulers.newThread())
                 .flatMap { list ->
                     Observable.fromIterable(list).flatMap { cityResponse ->
                        remote.getWeather(cityResponse.key, "true")
                            .map{ weatherResponse ->
                                weatherResponse.first().mapToLocal(cityResponse.key)
                            }
                            .toObservable()
                            .doOnNext {
                                Log.d(REMOTE+1, it.toString())
                                local.addWeatherInCity(
                                    cityResponse.mapToLocal(it.temperature), it)
                            }.map {cityResponse.mapToLocal(it.temperature)}
                    }.toList()
                 }.onErrorResumeNext{
                     Log.d(REMOTE+2, it.localizedMessage?:it.toString())
                     local.getCities(city)
                 }
         }

    fun getCityWeather(cityId: String): Single<CityWeather> {
        return local.getCityWeather(cityId)
    }

    fun updateWeather(cityId: String):Single<Weather> {
        return remote.getWeather(cityId, "true")
            .map {
                it.first().mapToLocal(cityId)
            }
            .doOnSuccess { local.addWeather(it) }
            .onErrorResumeNext {local.getWeather(cityId)}
    }

}




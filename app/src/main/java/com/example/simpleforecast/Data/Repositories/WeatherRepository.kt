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

    fun getCites(city: String):Observable<CityTemperature> {
        val obserVList = mutableListOf<Single<CityTemperature>>()
             return remote.getCities(city)
                .subscribeOn(Schedulers.newThread())
                 .flatMapObservable { list ->
                     Observable.fromIterable(list).flatMap { cityResponse ->
                        remote.getWeather(cityResponse.key)
                            .map{
                                CityTemperature(
                                    cityName = cityResponse.localizedName,
                                    area = cityResponse.administrativeArea.localizedName,
                                    temperature = it.first().temperature.metric.value.toString())
                            }
                            .toObservable()
                            .doOnNext {
                                local.addCity(City(
                                    id = cityResponse.key,
                                    name = cityResponse.localizedName,
                                    area = cityResponse.administrativeArea.localizedName
                                ))
                            }
                    }
                 }
    }

//                .flatMap { cites -> {
//                     remote.getWeather(cites.first().key).map{
//                        CityTemperature(cityName = cites.first().localizedName,
//                        area = cites.first().administrativeArea.localizedName,
//                        temperature = it.first().temperature.metric.value)
//                    }
//                } }
//                .flatMap{response -> response.map { cityResponse ->
//                    remote.getWeather(cityResponse.key)
//                        .map {
//                        CityTemperature(cityName = cityResponse.localizedName,
//                        area = cityResponse.administrativeArea.localizedName,
//                        temperature = it.first().temperature.metric.value)
//                        }
//
//                }}



//        remote.getCities(city)
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe({
//                _weatherResponse.postValue(it)
//            }, {
//                _weatherResponse.postValue(null)
//                _responseState.postValue(Pair(ResponseState.ERROR,
//                    it.message?:it.toString()))
//                Log.d("test", it.message)
//            }, {
//                _responseState.postValue(Pair(ResponseState.SUCCESS, ""))
//            })


//        list.forEach { locationId ->
//            obserVList.add(remote.getWeather(locationId)
//                .map {it.first()}
//                .map{
//                    Weather(
//                        temperature = it.temperature.metric.value.toString(),
//                        temperatureDescription = it.weatherText,
//                        icon = it.weatherIcon,
//                        cityId = locationId
//                    )
//                })
//        }
//        return Single.merge(obserVList)
    }




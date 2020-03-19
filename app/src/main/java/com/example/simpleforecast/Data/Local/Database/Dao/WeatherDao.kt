package com.example.simpleforecast.Data.Local.Database.Dao

import androidx.room.*
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.Data.Local.Database.Entity.Weather
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities WHERE name=:city")
    fun getCities(city:String): Single<List<City>>

    @Query("SELECT * FROM weathers WHERE city_id=:cityId")
    fun getWeather(cityId:String): Single<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(city:City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeather(weather:Weather)


    @Transaction
    fun addWeatherInCity(city:City, weather:Weather) {
        addCity(city)
        addWeather(weather)
    }




}
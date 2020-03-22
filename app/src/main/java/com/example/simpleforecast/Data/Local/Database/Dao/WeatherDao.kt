package com.example.simpleforecast.Data.Local.Database.Dao

import androidx.room.*
import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.example.simpleforecast.Data.Local.Database.Entity.CityWeather
import com.example.simpleforecast.Data.Local.Database.Entity.Weather
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities WHERE name LIKE :city")
    fun getCities(city: String): Single<List<City>>

    @Query("SELECT * FROM weathers WHERE city_id=:cityId")
    fun getWeather(cityId: String): Single<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeather(weather: Weather)


    @Query("SELECT weathers.*, cities.* FROM weathers INNER JOIN cities ON weathers.city_id = cities.id WHERE weathers.city_id = :cityId")
    fun getCityWeather(cityId: String): Single<CityWeather>

    @Transaction
    fun addWeatherInCity(city: City, weather: Weather) {
        addCity(city)
        addWeather(weather)
    }


}
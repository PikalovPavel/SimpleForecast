package com.example.simpleforecast.Data.Local.Database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simpleforecast.Data.Local.Database.Entity.City
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities WHERE name=:city")
    fun getCities(city:String): Single<List<City>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(city:City)

}
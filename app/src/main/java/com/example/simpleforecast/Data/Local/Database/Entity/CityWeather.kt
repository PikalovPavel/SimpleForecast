package com.example.simpleforecast.Data.Local.Database.Entity

import androidx.room.ColumnInfo
import androidx.room.Embedded


 class CityWeather(
     @Embedded
     val city:City,
     @Embedded
     val weather: Weather)
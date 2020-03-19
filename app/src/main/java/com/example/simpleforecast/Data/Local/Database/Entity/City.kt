package com.example.simpleforecast.Data.Local.Database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpleforecast.Data.Local.Util.CityTemperature


@Entity(tableName = "cities")
data class City(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "area")
    val area:String,
    @ColumnInfo(name = "areaType")
    val areaType:String,
    @ColumnInfo(name = "temperature")
    val temperature: String,
    @ColumnInfo(name = "country")
    val country:String
  )
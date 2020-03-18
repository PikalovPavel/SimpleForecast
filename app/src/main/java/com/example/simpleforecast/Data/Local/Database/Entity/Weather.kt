package com.example.simpleforecast.Data.Local.Database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.simpleforecast.Data.Local.Database.Entity.City


@Entity(tableName = "weathers",
    foreignKeys = [ForeignKey(
        entity = City::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("city_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "temperature")
    val temperature:String,
    @ColumnInfo(name = "temperature_description")
    val temperatureDescription:String,
    @ColumnInfo(name = "icon")
    val icon:Int,
    @ColumnInfo(name = "city_id")
    val cityId:String)
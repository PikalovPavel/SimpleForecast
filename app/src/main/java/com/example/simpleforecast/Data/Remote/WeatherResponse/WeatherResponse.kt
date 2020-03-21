package com.example.simpleforecast.Data.Remote.WeatherResponse


import com.example.simpleforecast.Data.Local.Database.Entity.Weather
import com.example.simpleforecast.Util.barConverter
import com.example.simpleforecast.Util.directionConverter
import com.example.simpleforecast.Util.kmToMsConverter
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("EpochTime")
    val epochTime: Int,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("IsDayTime")
    val isDayTime: Boolean,
    @SerializedName("Link")
    val link: String,
    @SerializedName("LocalObservationDateTime")
    val localObservationDateTime: String,
    @SerializedName("PrecipitationType")
    val precipitationType: Any,
    @SerializedName("Temperature")
    val temperature: Temperature,
    @SerializedName("RealFeelTemperature")
    val realFeelTemperature: RealFeelTemperature,
    @SerializedName("Wind")
    val wind: Wind,
    @SerializedName("Pressure")
    val pressure: Pressure,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int,
    @SerializedName("WeatherText")
    val weatherText: String
) {
    fun mapToLocal(cityId:String) =  Weather(
        icon = weatherIcon,
        cityId = cityId,
        temperature = temperature.metric.value,
        temperatureDescription = weatherText,
        temperatureFeels = realFeelTemperature.metric.value,
        wind = if (wind.speed.metric.value==null) wind.speed.metric.value else kmToMsConverter(wind.speed.metric.value),
        windDirection = directionConverter(wind.direction.localized),
        pressure = if (pressure.metric.value==null) pressure.metric.value else barConverter(pressure.metric.value)
        )
}
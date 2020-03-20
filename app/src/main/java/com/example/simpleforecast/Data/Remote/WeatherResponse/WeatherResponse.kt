package com.example.simpleforecast.Data.Remote.WeatherResponse


import com.example.simpleforecast.Data.Local.Database.Entity.Weather
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
        wind = wind.speed.metric.value,
        windDirection = wind.direction.localized,
        pressure = pressure.metric.value
        )
}
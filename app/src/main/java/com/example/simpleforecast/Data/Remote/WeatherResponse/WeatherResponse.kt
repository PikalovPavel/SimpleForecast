package com.example.simpleforecast.Data.Remote.WeatherResponse


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
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("PrecipitationType")
    val precipitationType: Any,
    @SerializedName("Temperature")
    val temperature: Temperature,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int,
    @SerializedName("WeatherText")
    val weatherText: String
)
package com.example.simpleforecast.Data.Remote.WeatherResponse


import com.google.gson.annotations.SerializedName

data class Pressure(
    @SerializedName("Metric")
    val metric: Metric
)
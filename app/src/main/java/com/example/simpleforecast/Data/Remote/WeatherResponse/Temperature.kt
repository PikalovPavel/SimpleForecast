package com.example.simpleforecast.Data.Remote.WeatherResponse


import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("Metric")
    val metric: Metric
)
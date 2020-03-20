package com.example.simpleforecast.Data.Remote.WeatherResponse


import com.google.gson.annotations.SerializedName

data class Pressure(
    @SerializedName("Imperial")
    val imperial: Imperial,
    @SerializedName("Metric")
    val metric: Metric
)
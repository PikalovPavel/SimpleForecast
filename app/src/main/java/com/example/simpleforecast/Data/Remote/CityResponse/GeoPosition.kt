package com.example.simpleforecast.Data.Remote.CityResponse


import com.google.gson.annotations.SerializedName

data class GeoPosition(
    @SerializedName("Elevation")
    val elevation: Elevation,
    @SerializedName("Latitude")
    val latitude: Double,
    @SerializedName("Longitude")
    val longitude: Double
)
package com.example.simpleforecast.Data.Remote.CityResponse


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("LocalizedName")
    val localizedName: String
)
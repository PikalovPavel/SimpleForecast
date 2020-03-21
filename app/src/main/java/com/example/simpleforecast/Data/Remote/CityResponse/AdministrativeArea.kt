package com.example.simpleforecast.Data.Remote.CityResponse


import com.google.gson.annotations.SerializedName

data class AdministrativeArea(
    @SerializedName("LocalizedName")
    val localizedName: String,
    @SerializedName("LocalizedType")
    val localizedType: String
)
package com.example.simpleforecast.Data.Remote.CityResponse


import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("AdministrativeArea")
    val administrativeArea: AdministrativeArea,
    @SerializedName("Country")
    val country: Country,
    @SerializedName("Key")
    val key: String,
    @SerializedName("LocalizedName")
    val localizedName: String
)
{
    fun mapToLocal(temperature: Double?) = City(
        id = key,
        name = localizedName,
        area = administrativeArea.localizedName,
        areaType = administrativeArea.localizedType,
        temperature = temperature,
        country = country.localizedName)
}
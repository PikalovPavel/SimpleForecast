package com.example.simpleforecast.Data.Remote.CityResponse


import com.example.simpleforecast.Data.Local.Database.Entity.City
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("AdministrativeArea")
    val administrativeArea: AdministrativeArea,
    @SerializedName("Country")
    val country: Country,
    @SerializedName("DataSets")
    val dataSets: List<String>,
    @SerializedName("EnglishName")
    val englishName: String,
    @SerializedName("GeoPosition")
    val geoPosition: GeoPosition,
    @SerializedName("IsAlias")
    val isAlias: Boolean,
    @SerializedName("Key")
    val key: String,
    @SerializedName("LocalizedName")
    val localizedName: String,
    @SerializedName("PrimaryPostalCode")
    val primaryPostalCode: String,
    @SerializedName("SupplementalAdminAreas")
    val supplementalAdminAreas: List<SupplementalAdminArea>,
    @SerializedName("TimeZone")
    val timeZone: TimeZone,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Version")
    val version: Int
)
{
    fun mapToLocal(temperature: Double) = City(
        id = key,
        name = localizedName,
        area = administrativeArea.localizedName,
        areaType = administrativeArea.localizedType,
        temperature = temperature,
        country = country.localizedName)
}
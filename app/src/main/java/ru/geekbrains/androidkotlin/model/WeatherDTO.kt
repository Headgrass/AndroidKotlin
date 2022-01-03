package ru.geekbrains.androidkotlin.model

import com.google.gson.annotations.SerializedName

data class WeatherDTO (
    val now: Long?,
    val fact: FactDTO?,
)

data class FactDTO (
    @SerializedName("_temp")
    val temp: Int?,
    @SerializedName("_condition")
    val condition: String?,
    @SerializedName("_humidity")
    val humidity: Int?,
    @SerializedName("_wind_speed")
    val wind_speed: Int?,
    @SerializedName("_dir")
    val dir: Int?,
)
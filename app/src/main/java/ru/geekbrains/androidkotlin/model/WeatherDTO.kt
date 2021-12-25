package ru.geekbrains.androidkotlin.model

import com.google.gson.annotations.SerializedName

data class WeatherDTO (
    val now: Long?,
    val fact: FactDTO?,
)

data class FactDTO (
    @SerializedName("temp")
    val temp: Int?,
    @SerializedName("condition")
    val condition: String?,
    @SerializedName("humidity")
    val humidity: Int?,
    @SerializedName("wind_speed")
    val wind_speed: Int?,
    @SerializedName("dir")
    val dir: Int?,
)
package ru.geekbrains.androidkotlin.model

data class Weather(
    val city: String = "Moscow",
    val temp: Int = 0,
    val condition: String = "Ясно",
    val wind_speed: Int = 5,
    val wind_dir: String = "Восточный"
)



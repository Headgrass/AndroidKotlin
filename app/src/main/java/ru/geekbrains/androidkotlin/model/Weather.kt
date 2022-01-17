package ru.geekbrains.androidkotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temp: Int = 0,
    val condition: String = "Ясно",
    val icon: String = ""

    ) : Parcelable

@Parcelize
data class City(
    val name: String = "Москва",
    val lat: Double = 0.0,
    val lon: Double = 0.0
) : Parcelable

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)


fun getRussianCities(): List<Weather> = listOf(
    Weather(City("Москва", 55.755826, 37.617299900000035), 1, "Пасмурно"),
    Weather(City("Санкт-Петербург", 59.9342802, 30.335098600000038), 3, "Ветренно"),
    Weather(City("Новосибирск", 55.00835259999999, 82.93573270000002), 5, "Ветренно"),
    Weather(City("Екатеринбург", 56.83892609999999, 60.60570250000001), 7, "Пасмурно"),
    Weather(City("Нижний Новгород", 56.2965039, 43.936059), 9, "Ясно"),
    Weather(City("Казань", 55.8304307, 49.06608060000008), 11, "Солнечно"),
    Weather(City("Челябинск", 55.1644419, 61.4368432), 13, "Ясно"),
    Weather(City("Омск", 54.9884804, 73.32423610000001), 15, "Ветренно"),
    Weather(City("Ростов-на-Дону", 47.2357137, 39.701505), 17, "Солнечно"),
    Weather(City("Уфа", 54.7387621, 55.972055400000045), 19, "Ветренно")
)


fun getWorldCities() = listOf(
    Weather(City("Лондон", 51.5085300, -0.1257400), 1, "Пасмурно"),
    Weather(City("Токио", 35.6895000, 139.6917100), 3, "Пасмурно"),
    Weather(City("Париж", 48.8534100, 2.3488000), 5, "Солнечно"),
    Weather(City("Берлин", 52.52000659999999, 13.404953999999975), 7, "Ясно"),
    Weather(City("Рим", 41.9027835, 12.496365500000024), 9, "Пасмурно"),
    Weather(City("Минск", 53.90453979999999, 27.561524400000053), 11, "Солнечно"),
    Weather(City("Стамбул", 41.0082376, 28.97835889999999), 13, "Ветренно"),
    Weather(City("Вашингтон", 38.9071923, -77.03687070000001), 15, "Ветренно"),
    Weather(City("Киев", 50.4501, 30.523400000000038), 17, "Солнечно"),
    Weather(City("Пекин", 39.90419989999999, 116.40739630000007), 19, "Солнечно")
)



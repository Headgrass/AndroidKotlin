package ru.geekbrains.androidkotlin.model

interface Repository {
    fun getWeatherFromServer(): Weather

    fun getWeatherFromLocalStorage(): Weather
}
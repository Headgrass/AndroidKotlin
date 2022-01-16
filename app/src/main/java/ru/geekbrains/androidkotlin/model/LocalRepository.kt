package ru.geekbrains.androidkotlin.model

interface LocalRepository {

    fun getAllHistory(): List<Weather>

    fun saveEntity(weather: Weather)
}
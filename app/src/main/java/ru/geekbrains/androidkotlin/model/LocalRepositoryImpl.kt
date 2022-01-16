package ru.geekbrains.androidkotlin.model

import java.util.*

class LocalRepositoryImpl(private val dao: HistoryDAO) : LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return dao.all()
            .map { entity ->
                Weather(
                    city = City(entity.city),
                    temp = entity.temperature,
                    condition = entity.condition
                )
            }
    }

    override fun saveEntity(weather: Weather) {
        dao.insert(
            HistoryEntity(
                id = 0,
                city = weather.city.name,
                temperature = weather.temp,
                condition = weather.condition,
                timestamp = Date().time
            )
        )
    }
}
package ru.geekbrains.androidkotlin.model

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
       return Weather()
    }

    override fun getWeatherFromLocalStorage(): Weather {
        return Weather()
    }
}
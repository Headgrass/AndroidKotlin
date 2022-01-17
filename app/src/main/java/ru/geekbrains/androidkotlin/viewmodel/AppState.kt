package ru.geekbrains.androidkotlin.viewmodel

import ru.geekbrains.androidkotlin.model.Weather

sealed class AppState {
    data class Success<T> (val data: T) : AppState()
    data class Error (val error: Throwable) : AppState()
    object Loading : AppState() // Не содержит данных, поэтому не data
}

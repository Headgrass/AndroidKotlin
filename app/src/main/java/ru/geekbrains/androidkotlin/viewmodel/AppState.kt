package ru.geekbrains.androidkotlin.viewmodel

sealed class AppState {
    data class Success (val weather: Any) : AppState()
    data class Error (val error: Throwable) : AppState()
    object Loading : AppState() // Не содержит данных, поэтому не data
}

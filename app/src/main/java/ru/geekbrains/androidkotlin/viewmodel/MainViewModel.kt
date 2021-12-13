package ru.geekbrains.androidkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.androidkotlin.model.Repository
import ru.geekbrains.androidkotlin.model.RepositoryImpl
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val liveDataObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl()

    fun getData(): LiveData<AppState> = liveDataObserve

    fun getWeather() {
        liveDataObserve.value = AppState.Loading

        Thread.sleep(5000)
        if(Random.nextBoolean()) {
            val weather = repo.getWeatherFromServer()
            liveDataObserve.postValue(AppState.Success(weather))
        }else liveDataObserve.postValue(AppState.Error(Exception("Проверьте интернет")))
    }

    override fun onCleared() {
        super.onCleared()
    }
}
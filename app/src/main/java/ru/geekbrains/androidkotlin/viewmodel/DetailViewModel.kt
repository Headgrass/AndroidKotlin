package ru.geekbrains.androidkotlin.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.androidkotlin.model.*
import ru.geekbrains.androidkotlin.view.App
import java.lang.Thread.sleep

class DetailViewModel : ViewModel() {
    private val liveDataObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl
    private val localRepo: LocalRepository = LocalRepositoryImpl(App.getHistoryDAO())

    fun getData(): LiveData<AppState> = liveDataObserve

    fun saveHistory(weather: Weather) {
        localRepo.saveEntity(weather)
    }

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)
    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)


    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataObserve.postValue(
                AppState.Success(
                    if (isRussian){
                        repo.getWeatherFromLocalStorageRus() } else {
                        repo.getWeatherFromLocalStorageWorld()
                    })
            )
        }.start()
    }
}
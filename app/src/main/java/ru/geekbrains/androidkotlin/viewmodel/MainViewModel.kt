package ru.geekbrains.androidkotlin.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.androidkotlin.model.Repository
import ru.geekbrains.androidkotlin.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repo: Repository = RepositoryImpl
) : ViewModel() {

    fun getData(): LiveData<AppState> = liveDataObserve

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)
    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataObserve.value = AppState.Loading
        Thread {
            sleep(1000)
                   val weather = if (isRussian){
                        repo.getWeatherFromLocalStorageRus() } else {
                        repo.getWeatherFromLocalStorageWorld()
                    }
            liveDataObserve.postValue(AppState.Success(weather))
        }.start()
    }
}
package ru.geekbrains.androidkotlin.model

import android.content.Context
import androidx.work.*

class MainWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        var result = Result.success()
        WeatherLoader.load(city = City(), object : WeatherLoader.OnWeatherLoadListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                result = Result.success()
            }

            override fun onFailed(throwable: Throwable) {
                result = Result.failure()
            }

        })
        return result
    }

    companion object {
        fun startWorker(context: Context) {
            val uploadRequest: WorkRequest =
                OneTimeWorkRequest.Builder(MainWorker::class.java)
                    .setConstraints(Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build())
                    .build()

            WorkManager.getInstance(context).enqueue(uploadRequest)
        }
    }
}
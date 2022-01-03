package ru.geekbrains.androidkotlin.model

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log


class MainIntentService : IntentService("MainIntentService") {

    companion object {
        const val TAG = "MainIntentService"
    }

    override fun onHandleIntent(intent: Intent?) {

        Log.d(TAG, "onHandleIntent")

        Thread.sleep(5000)

        intent?.getParcelableExtra<Weather>("WEATHER_EXTRA")?.let { weather ->
            WeatherLoader.load(weather.city, object : WeatherLoader.OnWeatherLoadListener {
                override fun onLoaded(weatherDTO: WeatherDTO) {
                    applicationContext.sendBroadcast(Intent(applicationContext, MainReceiver::class.java).apply {
                        action = MainReceiver.WEATHER_LOAD_SUCCESS
                        putExtra("WEATHER_EXTRA", Weather(
                            temp = weatherDTO.fact?.temp ?: 0,
                            condition = weatherDTO.fact?.condition ?: "Солнечно"
                        ))
                    })
                }

                override fun onFailed(throwable: Throwable) {
                    applicationContext.sendBroadcast(Intent(applicationContext, MainReceiver::class.java).apply {
                        action = MainReceiver.WEATHER_LOAD_FAILED
                    })
                }

            })
        }

    }


    override fun onCreate() {
        super.onCreate()
        Log.d(WeatherService.TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        Log.d(WeatherService.TAG, "onStartCommand")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(WeatherService.TAG, "onDestroy")

    }
}
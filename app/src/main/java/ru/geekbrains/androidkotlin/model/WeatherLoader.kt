package ru.geekbrains.androidkotlin.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {
    private const val MY_API_KEY =""

    fun load(city: City, listener: OnWeatherLoadListener) {

        val handler = Handler(Looper.getMainLooper())

        Thread {
            var urlConnection: HttpsURLConnection? = null

            try {
                val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")

                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.addRequestProperty("X-Yandex_API-Key", MY_API_KEY)
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 1000
                urlConnection.connectTimeout = 1000
                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    reader.lines().collect(Collectors.joining("\n"))
                } else {
                    ""
                }
                Log.d("DEBUGLOG", "result: $result")

                val weatherDTO = Gson().fromJson(result, WeatherDTO::class.java)
                handler.post {
                    listener.onLoaded(weatherDTO = weatherDTO)
                }


            } catch (e: Exception) {
                handler.post {
                    listener.onFailed(e)
                }
                Log.e("DEBUGLOG", "FAIL CONNECTION", e)
            } finally {
                urlConnection?.disconnect()
            }
        }.start()
    }

    interface OnWeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }
}
package ru.geekbrains.androidkotlin.model

import android.os.Build
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .callTimeout(1000, TimeUnit.MILLISECONDS)
        .connectTimeout(1000, TimeUnit.MILLISECONDS)
        .addInterceptor(Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("X-Yandex-API-Key", MY_API_KEY)
                    .build()
            )
        })
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        .build()

    private val weatherAPI: WeatherAPI = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .client(client)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build()
        .create(WeatherAPI::class.java)

    private const val MY_API_KEY = "6cec9727-0504-4083-b5e6-926f3ade13c6"

    fun load(city: City, listener: OnWeatherLoadListener) {


        var urlConnection: HttpsURLConnection? = null

        try {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")

            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.addRequestProperty("X-Yandex-API-Key", MY_API_KEY)
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
            listener.onLoaded(weatherDTO = weatherDTO)

        } catch (e: Exception) {
            listener.onFailed(e)
            Log.e("DEBUGLOG", "FAIL CONNECTION", e)
        } finally {
            urlConnection?.disconnect()
        }
    }

    fun loadOkHttp(city: City, listener: OnWeatherLoadListener) {
        val request: Request = Request.Builder()
            .get()
            .addHeader("X-Yandex-API-Key", MY_API_KEY)
            .url("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                listener.onFailed(e)
                Log.e("DEBUGLOG", "FAIL CONNECTION", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val weatherDTO =
                        Gson().fromJson(response.body?.string(), WeatherDTO::class.java)
                    listener.onLoaded(weatherDTO)
                } else {
                    listener.onFailed(Exception(response.body?.string()))
                    Log.e("DEBUGLOG", "FAIL CONNECTION $response")
                }
            }

        })
    }

    fun loadRetrofit(city: City, listener: OnWeatherLoadListener) {

        weatherAPI.getWeather(MY_API_KEY, city.lat, city.lon)
            .enqueue(object : retrofit2.Callback<WeatherDTO> {
                override fun onResponse(
                    call: retrofit2.Call<WeatherDTO>,
                    response: retrofit2.Response<WeatherDTO>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { listener.onLoaded(it) }
                    } else {
                        listener.onFailed(Exception(response.message()))
                        Log.e("DEBUGLOG", "FAIL CONNECTION $response")
                    }
                }

                override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
                    listener.onFailed(t)
                }

            })
    }

    interface OnWeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }
}
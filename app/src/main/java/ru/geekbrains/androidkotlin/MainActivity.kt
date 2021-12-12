package ru.geekbrains.androidkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}


data class Weather (var town: String, var temperature: Int)

class Repository {

    private val weatherList: List<Weather>

    init {
        weatherList = listOf(Weather("Москва", -5))
    }
}
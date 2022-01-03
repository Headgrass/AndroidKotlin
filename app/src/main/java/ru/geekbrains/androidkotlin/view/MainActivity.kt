package ru.geekbrains.androidkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import ru.geekbrains.androidkotlin.R
import ru.geekbrains.androidkotlin.databinding.ActivityMainBinding
import ru.geekbrains.androidkotlin.model.MainWorker
import ru.geekbrains.androidkotlin.model.RepositoryImpl

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val json = Gson().toJson(RepositoryImpl.getWeatherFromServer())
        Log.d("DEBUGLOG", json)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MainFragment.newInstance())
            .commit()

        MainWorker.startWorker(this)

    }
}

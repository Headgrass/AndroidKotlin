package ru.geekbrains.androidkotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.contacts) {
            if(item.itemId == R.id.contacts) {
                startActivity(Intent(this, ContactsActivity::class.java))
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

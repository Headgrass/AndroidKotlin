package ru.geekbrains.androidkotlin.view

import android.app.Application
import androidx.room.Room
import ru.geekbrains.androidkotlin.model.HistoryDAO
import ru.geekbrains.androidkotlin.model.HistoryDataBase

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDAO(): HistoryDAO {

            if (db == null) {
                synchronized(HistoryDataBase::class.java) {
                    if (db == null) {
                        appInstance?.let { app ->
                            db = Room.databaseBuilder(
                                app.applicationContext,
                                HistoryDataBase::class.java,
                                DB_NAME

                                    ).build()
                        } ?: throw Exception("WTF?")
                    }
                }
            }

            return  db!!.historyDAO()
        }
    }
}
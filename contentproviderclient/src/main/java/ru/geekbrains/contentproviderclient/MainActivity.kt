package ru.geekbrains.contentproviderclient

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

private val HISTORY_URI: Uri = Uri.parse("content://geekbrains.provider/HistoryEntity")

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cursor: Cursor? = contentResolver.query(HISTORY_URI, null, null, null, null)

        val list = mutableListOf<String>()

        cursor?.let {
            for (i in 0..cursor.count) {
                if (cursor.moveToPosition(i)) {
                    val id = cursor.getLong(cursor.getColumnIndex("id"))
                    val city = cursor.getString(cursor.getColumnIndex("city"))
                    val temperature = cursor.getInt(cursor.getColumnIndex("temperature"))

                    list.add("$id - $city - $temperature")
                }
            }

            AlertDialog.Builder(this)
                .setItems(list.toTypedArray()) { _, _ -> }
                .setPositiveButton("OK") { _, _ -> }
                .create()

            cursor.close()
        }
    }
}
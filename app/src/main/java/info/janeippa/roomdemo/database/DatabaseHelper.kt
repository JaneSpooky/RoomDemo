package info.janeippa.roomdemo.database

import android.content.Context
import androidx.room.Room

object DatabaseHelper {

    private const val DATABASE_NAME = "demo_app_database"

    fun getAppDatabase(context: Context): AppDatabase
        = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
}
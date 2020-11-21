package info.janeippa.roomdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import info.janeippa.roomdemo.database.dao.BatteryDao
import info.janeippa.roomdemo.database.entity.Battery

@Database(entities = [Battery::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun batteryDao(): BatteryDao
}
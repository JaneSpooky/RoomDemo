package info.janeippa.roomdemo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import info.janeippa.roomdemo.database.entity.Battery
import java.util.*

@Dao
interface BatteryDao {

    @Query("SELECT * FROM battery")
    fun getAll(): List<Battery>

    @Query("SELECT * FROM battery WHERE id IN (:batteryId)")
    fun loadAllByIds(batteryId: IntArray): List<Battery>

//    @Query("SELECT * FROM battery WHERE created_at BETWEEN :start AND :end")
//    fun findBetweenTime(start: Date, end: Date): List<Battery>

    @Insert
    fun insert(battery: Battery)

    @Delete
    fun delete(battery: Battery)
}
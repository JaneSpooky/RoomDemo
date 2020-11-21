package info.janeippa.roomdemo.database.entity

import android.os.BatteryManager
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Battery {
    @PrimaryKey
    var id: Long = System.currentTimeMillis()
    @ColumnInfo(name = "battery_level")
    var batteryLevel = 0
    @ColumnInfo(name = "battery_state")
    var batteryState = 0
    @ColumnInfo(name = "battery_temperature")
    var batteryTemperature = 0
    @ColumnInfo(name = "battery_voltage")
    var batteryVoltage = 0
    @ColumnInfo(name = "created_at")
    var createdAt = System.currentTimeMillis()
    @Ignore
    var state: String = ""
        get() = when(batteryState) {
            BatteryManager.BATTERY_HEALTH_COLD -> "低温"
            BatteryManager.BATTERY_HEALTH_DEAD -> "故障"
            BatteryManager.BATTERY_HEALTH_GOOD -> "良好"
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "電圧オーバー"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> "オーバーヒート"
            BatteryManager.BATTERY_HEALTH_UNKNOWN -> "状態不明"
            else -> "未確認エラー"
        }
}

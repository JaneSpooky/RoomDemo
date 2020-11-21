package info.janeippa.roomdemo

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.annotation.WorkerThread
import info.janeippa.roomdemo.database.AppDatabase
import info.janeippa.roomdemo.database.DatabaseHelper
import info.janeippa.roomdemo.database.entity.Battery
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private val database: AppDatabase by lazy { DatabaseHelper.getAppDatabase(applicationContext) }

    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    override fun onPause() {
        super.onPause()
        cancelTimer()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
    }

    private fun initClick() {
        button.setOnClickListener {
            showLog()
        }
    }

    private fun startTimer() {
        cancelTimer()
        timer = Timer().apply {
            schedule(object: TimerTask() {
                override fun run() {
                    insertBattery()
                }
            }, TIMER_DELAY, TIMER_PERIOD)
        }
    }

    private fun cancelTimer() {
        timer?.cancel()
        timer = null
    }

    private fun insertBattery() {
        Log.d("MainActivity", "insertBattery start")
        val intent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED)) ?: return
        val battery = Battery().apply {
            batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            batteryState = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)
            batteryTemperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10 // ℃
            batteryVoltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) / 1000 // V
        }
        database.batteryDao().insert(battery)
        Log.d("MainActivity", "insertBattery end")
    }

    private fun showLog() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = database.batteryDao().getAll()//.sortedBy { it.createdAt }
            withContext(Dispatchers.Main) {
                textView.text = list.map { makeLog(it) }.joinToString(separator = "\n\n")
            }
        }
    }

    private fun makeLog(battery: Battery): String =
        StringBuilder()
            .append(DateFormat.format("yyyy/MM/dd HH:mm:ss", battery.createdAt))
            .append("----------\n")
            .append("残量:${battery.batteryLevel}%\n")
            .append("状態:${battery.state}\n")
            .append("温度:${battery.batteryTemperature}℃\n")
            .append("電圧:${battery.batteryVoltage}V")
            .toString()

    companion object {
        private const val TIMER_DELAY = 0L
        private const val TIMER_PERIOD = 60000L
    }
}
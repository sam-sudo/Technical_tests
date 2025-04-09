package com.ck.basfchallenge.data.local.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import com.ck.basfchallenge.domain.models.battery.BatteryInfoModel
import com.ck.basfchallenge.domain.models.battery.BatteryStatus
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

object BatteryUtils {
    fun observeBatteryInfo(context: Context) = callbackFlow<BatteryInfoModel> {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                Log.w("TAG", "onReceive battery")
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val statusInt = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN)
                val temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)

                val percentage = if (level >= 0 && scale > 0) {
                    (level * 100f / scale).toInt()
                } else 0

                val temperatureC = if (temp > 0) temp / 10f else 0f

                val batteryStatus = when (statusInt) {
                    BatteryManager.BATTERY_STATUS_CHARGING -> BatteryStatus.CHARGING
                    BatteryManager.BATTERY_STATUS_DISCHARGING -> BatteryStatus.DISCHARGING
                    BatteryManager.BATTERY_STATUS_FULL -> BatteryStatus.FULL
                    BatteryManager.BATTERY_STATUS_NOT_CHARGING -> BatteryStatus.NOT_CHARGING
                    else -> BatteryStatus.UNKNOWN
                }

                val updateUI:  ChannelResult<Unit> = trySend(
                    BatteryInfoModel(
                        percentage = percentage,
                        temperatureC = temperatureC,
                        status = batteryStatus
                    )
                )
                Log.w("BatteryUtils", "updateUI -> ${updateUI.isSuccess}")
            }
        }
        context.registerReceiver(receiver, filter)
        awaitClose { context.unregisterReceiver(receiver) }
    }.distinctUntilChanged()
    //Use distinctUntilChanged here in data layer to not sent in loop the same state and
    //sent to the ui just the states that are different
}
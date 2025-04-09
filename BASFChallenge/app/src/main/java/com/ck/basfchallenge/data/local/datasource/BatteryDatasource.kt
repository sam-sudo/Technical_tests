package com.ck.basfchallenge.data.local.datasource

import android.app.ApplicationErrorReport.BatteryInfo
import android.content.Context
import com.ck.basfchallenge.data.local.utils.BatteryUtils
import com.ck.basfchallenge.domain.models.battery.BatteryInfoModel
import com.ck.basfchallenge.domain.models.battery.BatteryStatus
import com.ck.basfchallenge.domain.repository.BatteryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import javax.inject.Inject

class BatteryDatasource @Inject constructor(
    private val context: Context
): BatteryRepository {

    override fun observeBatteryInfo(): Flow<BatteryInfoModel> {
        return BatteryUtils.observeBatteryInfo(context)
    }

    override fun observeBatteryLowEvents(): Flow<String> = flow {
        var eventEmitted = false
        BatteryUtils.observeBatteryInfo(context).collect { batteryInfo ->
            if (!eventEmitted && batteryInfo.percentage < 58) {
                emit("Battery below 20%!")
                eventEmitted = true
            } else if (batteryInfo.percentage >= 58) {
                eventEmitted = false
            }
        }
    }
    /*override fun observeBatteryLowEvents(): Flow<String> =
         BatteryUtils.observeBatteryInfo(context)
             .scan<Pair<BatteryInfoModel?, BatteryInfoModel>>(null to BatteryInfoModel(0, 0f, BatteryStatus.UNKNOWN)) { previous, current ->
                 previous.second to current
             }
            .filter { (old,new) ->
                old == null || (old.percentage >= 20 && new.percentage < 20)
            }
            .map { "Battery below 20%!" }
            .distinctUntilChanged()*/
}
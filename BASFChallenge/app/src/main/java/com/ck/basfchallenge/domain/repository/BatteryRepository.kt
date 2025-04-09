package com.ck.basfchallenge.domain.repository

import com.ck.basfchallenge.domain.models.battery.BatteryInfoModel
import kotlinx.coroutines.flow.Flow

interface BatteryRepository {
    fun observeBatteryInfo(): Flow<BatteryInfoModel>
    fun observeBatteryLowEvents(): Flow<String>
}
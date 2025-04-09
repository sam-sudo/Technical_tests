package com.ck.basfchallenge.data.repository

import com.ck.basfchallenge.data.local.datasource.BatteryDatasource
import com.ck.basfchallenge.domain.models.battery.BatteryInfoModel
import com.ck.basfchallenge.domain.repository.BatteryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BatteryRepositoryImpl @Inject constructor(
    private val batteryDataSource: BatteryDatasource
) : BatteryRepository {
    override fun observeBatteryInfo(): Flow<BatteryInfoModel> {
        return batteryDataSource.observeBatteryInfo()
    }

    override fun observeBatteryLowEvents(): Flow<String> {
        return batteryDataSource.observeBatteryLowEvents()
    }
}
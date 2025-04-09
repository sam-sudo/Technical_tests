package com.ck.basfchallenge.domain.usecases.battery

import com.ck.basfchallenge.domain.models.battery.BatteryInfoModel
import com.ck.basfchallenge.domain.repository.BatteryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveBatteryInfoUseCase @Inject constructor(
    private val batteryRepository: BatteryRepository
) {
    operator fun invoke(): Flow<BatteryInfoModel> {
        return batteryRepository.observeBatteryInfo()
    }
}
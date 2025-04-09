package com.ck.basfchallenge.domain.usecases.battery

import com.ck.basfchallenge.domain.repository.BatteryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveBatteryLowEventUseCase @Inject constructor(
    private val batteryRepository: BatteryRepository
) {
    operator fun invoke(): Flow<String> {
        return batteryRepository.observeBatteryLowEvents()
    }
}
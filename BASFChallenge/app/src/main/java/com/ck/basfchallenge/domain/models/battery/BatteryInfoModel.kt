package com.ck.basfchallenge.domain.models.battery

data class BatteryInfoModel(
    val percentage: Int,
    val temperatureC: Float,
    val status: BatteryStatus
)
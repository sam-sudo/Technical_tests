package com.ck.basfchallenge.presentation.view.home

import com.ck.basfchallenge.domain.models.battery.BatteryInfoModel
import com.ck.basfchallenge.domain.models.battery.BatteryStatus
import com.ck.basfchallenge.domain.models.network.ConnectionType
import com.ck.basfchallenge.domain.models.network.NetworkStatusInfo
import com.ck.basfchallenge.domain.models.network.SignalStrength

data class HomeState(
    val isNetWorkAvailable: Boolean = false,
    val batteryStatus: BatteryInfoModel = BatteryInfoModel(
        percentage = 0,
        temperatureC = 0F,
        status = BatteryStatus.UNKNOWN
    ),
    val networkStatus: NetworkStatusInfo = NetworkStatusInfo(
        isConnected = false,
        connectionType = ConnectionType.NONE,
        signalStrength = SignalStrength.NONE
    ),
    val snackBarMessage: String? = null
)
package com.ck.basfchallenge.domain.models.network

data class NetworkStatusInfo(
    val isConnected: Boolean,
    val connectionType: ConnectionType,
    val signalStrength: SignalStrength,
    val ssid: String? = null
)

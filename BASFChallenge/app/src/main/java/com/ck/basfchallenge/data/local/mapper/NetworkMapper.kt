package com.ck.basfchallenge.data.local.mapper

import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import com.ck.basfchallenge.domain.models.network.ConnectionType
import com.ck.basfchallenge.domain.models.network.NetworkStatusInfo
import com.ck.basfchallenge.domain.models.network.SignalStrength

fun NetworkCapabilities.toStatusInfo(wifiManager: WifiManager): NetworkStatusInfo {
    val connected = hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    val type = when {
        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionType.WIFI
        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionType.CELLULAR
        else -> ConnectionType.NONE
    }
    val strength = if (type == ConnectionType.WIFI && connected) {
        val level = wifiManager.connectionInfo.rssi
        val signal = WifiManager.calculateSignalLevel(level, 5)
        when (signal) {
            0, 1 -> SignalStrength.LOW
            2, 3 -> SignalStrength.MEDIUM
            4 -> SignalStrength.HIGH
            else -> SignalStrength.NONE
        }
    } else SignalStrength.NONE

    val ssid = if (type == ConnectionType.WIFI && connected) {
        wifiManager.connectionInfo.ssid?.replace("\"", "")
    } else null

    return NetworkStatusInfo(
        isConnected = connected,
        connectionType = type,
        signalStrength = strength,
        ssid = ssid
    )
}

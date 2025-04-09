package com.ck.basfchallenge.data.local.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import com.ck.basfchallenge.data.local.mapper.toStatusInfo
import com.ck.basfchallenge.domain.models.network.ConnectionType
import com.ck.basfchallenge.domain.models.network.NetworkStatusInfo
import com.ck.basfchallenge.domain.models.network.SignalStrength
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

object NetworkUtils {
    fun observeNetworkStatus(context: Context) = callbackFlow {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val capabilities = manager.getNetworkCapabilities(network)
                val info = capabilities?.toStatusInfo(wifiManager) ?: NetworkStatusInfo(false, ConnectionType.NONE, SignalStrength.NONE)
                trySend(info)
            }
            override fun onLost(network: Network) {
                trySend(NetworkStatusInfo(false, ConnectionType.NONE, SignalStrength.NONE))
            }
            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                val info = networkCapabilities.toStatusInfo(wifiManager)
                trySend(info)
            }
        }
        manager.registerDefaultNetworkCallback(callback)
        awaitClose { manager.unregisterNetworkCallback(callback) }
    }.distinctUntilChanged()
    //Use distinctUntilChanged here in data layer to not sent in loop the same state and
    //sent to the ui just the states that are different

    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = manager.activeNetwork ?: return false
        val capabilities = manager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}
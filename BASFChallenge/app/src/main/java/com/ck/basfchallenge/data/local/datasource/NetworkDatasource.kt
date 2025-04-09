package com.ck.basfchallenge.data.local.datasource

import android.content.Context
import com.ck.basfchallenge.domain.models.network.NetworkStatusInfo
import com.ck.basfchallenge.data.local.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkDatasource @Inject constructor(
    private val context: Context
) {

    fun getNetWorkStatusListener(): Flow<NetworkStatusInfo> {
        return NetworkUtils.observeNetworkStatus(context)
    }

    fun isNetworkAvailable(): Boolean{
        return NetworkUtils.isNetworkAvailable(context)
    }

}
package com.ck.basfchallenge.data.repository

import com.ck.basfchallenge.data.local.datasource.NetworkDatasource
import com.ck.basfchallenge.domain.models.network.NetworkStatusInfo
import com.ck.basfchallenge.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow

class NetworkRepositoryImpl(
    private val networkDatasource: NetworkDatasource
): NetworkRepository {
    override fun observeNetworkStatus(): Flow<NetworkStatusInfo> {
        return networkDatasource.getNetWorkStatusListener()
    }

    override fun isNetworkAvailable(): Boolean {
        return networkDatasource.isNetworkAvailable()
    }
}
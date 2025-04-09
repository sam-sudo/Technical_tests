package com.ck.basfchallenge.domain.repository

import com.ck.basfchallenge.domain.models.network.NetworkStatusInfo
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun observeNetworkStatus(): Flow<NetworkStatusInfo>
    fun isNetworkAvailable(): Boolean
}
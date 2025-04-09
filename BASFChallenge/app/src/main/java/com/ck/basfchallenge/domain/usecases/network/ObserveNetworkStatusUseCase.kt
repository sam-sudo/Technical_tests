package com.ck.basfchallenge.domain.usecases.network

import com.ck.basfchallenge.domain.models.network.NetworkStatusInfo
import com.ck.basfchallenge.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNetworkStatusUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
){
    operator fun invoke(): Flow<NetworkStatusInfo> = networkRepository.observeNetworkStatus()
}
package com.ck.basfchallenge.domain.usecases.network

import com.ck.basfchallenge.domain.repository.NetworkRepository

class IsNetworkAvailableUseCase(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(): Boolean = networkRepository.isNetworkAvailable()
}
package com.ck.basfchallenge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ck.basfchallenge.domain.models.battery.BatteryInfoModel
import com.ck.basfchallenge.domain.models.battery.BatteryStatus
import com.ck.basfchallenge.domain.models.network.NetworkStatusInfo
import com.ck.basfchallenge.domain.usecases.battery.ObserveBatteryInfoUseCase
import com.ck.basfchallenge.domain.usecases.battery.ObserveBatteryLowEventUseCase
import com.ck.basfchallenge.domain.usecases.network.ObserveNetworkStatusUseCase
import com.ck.basfchallenge.presentation.view.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
    private val observeBatteryInfoUseCase: ObserveBatteryInfoUseCase,
    private val observeBatteryLowEventUseCase: ObserveBatteryLowEventUseCase
): ViewModel() {

    private val TAG = "HomeViewModel"

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _snackBarEvent = MutableSharedFlow<String>(replay = 0)
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            observeNetworkStatusUseCase().collect{ info ->
                Log.w(TAG, "observeNetworkStatusUseCase $info")
                _state.value = _state.value.copy(
                    networkStatus = info,
                    //snackBarMessage = "Network changed: ${info.connectionType}"
                )

                _snackBarEvent.emit("Network changed from sharedFlow: ${info.connectionType}")
            }
        }
        viewModelScope.launch {
            observeBatteryInfoUseCase().collect{info ->
                Log.w(TAG, "observeBatteryInfoUseCase $info")

                val oldBatteryStatus = _state.value.batteryStatus.status
                _state.value = _state.value.copy(batteryStatus = info)
                val newBatteryStatus = _state.value.batteryStatus.status
                /*if (info.percentage < 20 && oldBatteryInfo.percentage >= 20) {
                    *//*_state.value = _state.value.copy(
                        snackBarMessage = "Battery below 20%!"
                    )*//*
                    _snackBarEvent.emit("Battery below 20%!")

                }*/
                if (newBatteryStatus != oldBatteryStatus) {
                    _state.value = _state.value.copy(
                        snackBarMessage = "Battery status changed: $newBatteryStatus"
                    )
                }
            }
        }

        viewModelScope.launch {
            observeBatteryLowEventUseCase().collect{ message ->
                _snackBarEvent.emit(message)
            }
        }
    }

    fun clearSnackBarMessage() {
        _state.value = _state.value.copy(snackBarMessage = null)
    }

}
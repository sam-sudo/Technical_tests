package com.ck.basfchallenge.presentation.view.home

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Battery0Bar
import androidx.compose.material.icons.filled.Battery1Bar
import androidx.compose.material.icons.filled.Battery2Bar
import androidx.compose.material.icons.filled.Battery3Bar
import androidx.compose.material.icons.filled.Battery4Bar
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NetworkWifi
import androidx.compose.material.icons.filled.NetworkWifi1Bar
import androidx.compose.material.icons.filled.NetworkWifi2Bar
import androidx.compose.material.icons.filled.NetworkWifi3Bar
import androidx.compose.material.icons.filled.SignalCellular4Bar
import androidx.compose.material.icons.filled.SignalCellularConnectedNoInternet0Bar
import androidx.compose.material.icons.filled.SignalCellularOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ck.basfchallenge.R
import com.ck.basfchallenge.domain.models.battery.BatteryInfoModel
import com.ck.basfchallenge.domain.models.battery.BatteryStatus
import com.ck.basfchallenge.domain.models.network.ConnectionType
import com.ck.basfchallenge.domain.models.network.NetworkStatusInfo
import com.ck.basfchallenge.domain.models.network.SignalStrength
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    homeStateFlow: StateFlow<HomeState>,
    snackBarEvents: SharedFlow<String>,
    onClearMessage: () -> Unit
) {
    val homeState by homeStateFlow.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        Log.d("MyScreen", "LaunchedEffect unit")
        snackBarEvents.collect{ message ->
            snackBarHostState.showSnackbar(message = message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(title = { Text("BASF Challenge") })
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NetworkStatusCard(homeState.networkStatus)
            BatteryStatusCard(homeState.batteryStatus)
        }

        //val message = homeState.snackBarMessage
        /*if (message != null) {
            // Lanza un efecto cada vez que el mensaje cambie
            LaunchedEffect(message) {
                snackBarHostState.showSnackbar(message)
                onClearMessage() // Limpia el mensaje en el estado
            }
        }*/
    }
}

@Composable
fun NetworkStatusCard(networkStatusInfo: NetworkStatusInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NetworkIndicators(networkStatusInfo)
        }
    }
}

@Composable
fun NetworkIndicators(
    status: NetworkStatusInfo
) {
    val wifiInUse = status.connectionType == ConnectionType.WIFI && status.isConnected
    val dataInUse = status.connectionType == ConnectionType.CELLULAR && status.isConnected

    val wifiColor = when {
        wifiInUse -> Color(0xFF4CAF50)
        status.isConnected -> Color.Gray
        else -> Color.Red
    }
    val dataColor = when {
        dataInUse -> Color(0xFF4CAF50)
        status.isConnected -> Color.Gray
        else -> Color.Red
    }

    val wifiIcon = when {
        !wifiInUse -> Icons.Filled.WifiOff
        else -> when (status.signalStrength) {
            SignalStrength.NONE, SignalStrength.LOW -> Icons.Filled.NetworkWifi1Bar
            SignalStrength.MEDIUM -> Icons.Filled.NetworkWifi2Bar
            SignalStrength.HIGH -> Icons.Filled.NetworkWifi
        }
    }
    val dataIcon = if (dataInUse) Icons.Filled.SignalCellular4Bar else Icons.Filled.SignalCellularOff

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = wifiIcon,
            contentDescription = null,
            tint = wifiColor,
            modifier = Modifier.size(32.dp)
        )
        Icon(
            imageVector = dataIcon,
            contentDescription = null,
            tint = dataColor,
            modifier = Modifier.size(32.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = "SSID: ${status.ssid}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun BatteryStatusCard(batteryInfo: BatteryInfoModel) {
    val isCharging = batteryInfo.status == BatteryStatus.CHARGING

    val chargeIcons = listOf(
        Icons.Filled.Battery0Bar,
        Icons.Filled.Battery1Bar,
        Icons.Filled.Battery2Bar,
        Icons.Filled.Battery3Bar,
        Icons.Filled.Battery4Bar,
        Icons.Filled.BatteryFull
    )

    val infiniteTransition = rememberInfiniteTransition()
    val animatedIndex = if (isCharging) {
        // Animación de índice que sube y baja (ej. 0..5..0..5...)
        infiniteTransition.animateFloat(
            initialValue = 0F,
            targetValue = (chargeIcons.size).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1200),
                repeatMode = RepeatMode.Restart
            ), label = ""
        ).value
    } else {
        // Si no está cargando, escogemos un índice según la batería real
        when (batteryInfo.percentage) {
            in 0..10 -> 0
            in 11..30 -> 1
            in 31..50 -> 2
            in 51..70 -> 3
            in 71..90 -> 4
            else -> 5
        }
    }

    val batteryIcon = chargeIcons[animatedIndex.toInt()]

    val iconColor = when {
        batteryInfo.percentage < 20 -> Color.Red
        else -> Color(0xFF4CAF50)
    }

    val statusText = when (batteryInfo.status) {
        BatteryStatus.CHARGING -> "Charging"
        BatteryStatus.FULL -> "Full"
        BatteryStatus.DISCHARGING -> "Discharging"
        BatteryStatus.NOT_CHARGING -> "Not Charging"
        BatteryStatus.UNKNOWN -> "Unknown"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = batteryIcon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = "Battery: ${batteryInfo.percentage}%",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Temp: ${batteryInfo.temperatureC} °C",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Status: $statusText",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

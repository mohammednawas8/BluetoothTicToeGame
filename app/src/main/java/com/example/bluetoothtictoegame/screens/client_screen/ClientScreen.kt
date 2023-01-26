package com.example.bluetoothtictoegame.screens.client_screen


import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bluetoothtictoegame.MainActivity
import com.example.bluetoothtictoegame.screens.common.TicToeTopAppBar

@SuppressLint("MissingPermission")
@Composable
fun ClientScreen(
    navController: NavController,
    viewModel: ClientViewModel = hiltViewModel()
) {
    val context = LocalContext.current as MainActivity

    val state = viewModel.state.collectAsState().value


    ScanDevicesReceiver(context) {
       viewModel.newDeviceFound(it)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        TicToeTopAppBar(
            title = "Search Players",
            action = "Search",
            isActionLoading = state.searchingForHosts
        ) {
            viewModel.discoverDevices()
        }

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "New Devices",
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground

        )
        Divider(modifier = Modifier.fillMaxWidth().padding(10.dp))
        Spacer(modifier = Modifier.height(5.dp))
        state.newDevices.forEach {
            BluetoothDeviceItem(
                bluetoothDevice = it,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

            }
        }

        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Paired Devices",
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground
        )
        Divider(modifier = Modifier.fillMaxWidth().padding(10.dp))
        Spacer(modifier = Modifier.height(5.dp))
        state.alreadyPairedDevices.forEach {
            BluetoothDeviceItem(
                bluetoothDevice = it,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

            }
        }
    }
}

@Composable
private fun ScanDevicesReceiver(context: MainActivity, deviceFound: (BluetoothDevice) -> Unit) {
    DisposableEffect(key1 = true, effect = {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (action == BluetoothDevice.ACTION_FOUND) {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        deviceFound(it)
                    }
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        onDispose {
            context.unregisterReceiver(receiver)
        }
    })
}

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDeviceItem(
    bluetoothDevice: BluetoothDevice,
    modifier: Modifier = Modifier,
    onClick: (BluetoothDevice) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick(bluetoothDevice) },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = bluetoothDevice.name?:"Unknown - ${bluetoothDevice.address}",
            fontSize = 16.sp,
            color = MaterialTheme.colors.onBackground
        )
    }
}














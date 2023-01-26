package com.example.bluetoothtictoegame.screens.server_screen

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_SCAN_MODE_CHANGED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bluetoothtictoegame.MainActivity
import com.example.bluetoothtictoegame.screens.Navigation
import com.example.bluetoothtictoegame.screens.client_screen.components.AnimatedCircleLoading
import com.example.bluetoothtictoegame.screens.common.TicToeTopAppBar
import com.example.bluetoothtictoegame.screens.makePhoneDiscoverable
import kotlinx.coroutines.flow.SharedFlow

@SuppressLint("MissingPermission")
@Composable
fun ServerScreen(
    viewModel: ServerViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val error = viewModel.error

    val activity = LocalContext.current as MainActivity

    var askPhoneDiscoverableKey by remember {
        mutableStateOf(1)
    }

    //Show error toast whenever a new value comes into error state
    ShowServerErrorToast(context = activity, error = error)

    //Asking the user to make the phone discoverable
    AskUserToMakePhoneDiscoverable(
        key = askPhoneDiscoverableKey,
        activity = activity
    )

    //Broad cast receiver for scan state changing
    DiscoverableStateReceiver(
        activity = activity,
        onChange = { viewModel.changeDiscoverableState(it) }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        TicToeTopAppBar(
            title = "Lobby",
            action = "",
            isActionLoading = false,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            state.remoteBluetoothDevice?.let {
                Text(
                    text = "Connected to ${it.name ?: "Unknown - ${it.address}"}",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onBackground
                )
            }

            if (!state.isPhoneDiscoverable) {
                OutlinedButton(
                    onClick = { askPhoneDiscoverableKey++ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = "Make Discoverable", fontSize = 16.sp)
                }
            }

            if (state.isListening) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Waiting for a player to join ...",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onBackground
                    )

                    Box(modifier = Modifier.size(150.dp), contentAlignment = Alignment.Center) {
                        AnimatedCircleLoading(circleSize = 150f)
                    }

                }

            }
        }
    }
}

@Composable
fun ShowServerErrorToast(context: Context, error: SharedFlow<String>) {
    LaunchedEffect(key1 = true) {
        error.collect {
            Toast.makeText(context, "Unexpected error $it", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun AskUserToMakePhoneDiscoverable(key: Any, activity: MainActivity) {
    LaunchedEffect(key1 = key) {
        makePhoneDiscoverable(activity)
    }
}

@Composable
fun DiscoverableStateReceiver(
    activity: MainActivity,
    onChange: (currentState: Int) -> Unit
) {
    DisposableEffect(key1 = true, effect = {
        val discoverableReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == ACTION_SCAN_MODE_CHANGED) {
                    val currentScanState = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1)
                    onChange(currentScanState)
                }
            }
        }
        activity.registerReceiver(discoverableReceiver, IntentFilter(ACTION_SCAN_MODE_CHANGED))
        onDispose {
            activity.unregisterReceiver(discoverableReceiver)
        }
    })
}
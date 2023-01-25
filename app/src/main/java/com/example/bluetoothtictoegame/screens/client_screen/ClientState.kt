package com.example.bluetoothtictoegame.screens.client_screen

import android.bluetooth.BluetoothDevice

data class ClientState(
    val searchingForHosts: Boolean = false,
    val shouldEnableBluetooth: Boolean = false,
    val alreadyPairedDevices: List<BluetoothDevice> = emptyList()
) {
}
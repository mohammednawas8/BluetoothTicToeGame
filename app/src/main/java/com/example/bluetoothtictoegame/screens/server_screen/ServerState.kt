package com.example.bluetoothtictoegame.screens.server_screen

import android.bluetooth.BluetoothDevice

data class ServerState(
    val isListening: Boolean = false,
    val remoteBluetoothDevice: BluetoothDevice? = null,
    val isPhoneDiscoverable: Boolean = false
)
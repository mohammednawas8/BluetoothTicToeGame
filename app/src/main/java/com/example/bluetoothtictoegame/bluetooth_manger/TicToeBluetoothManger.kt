package com.example.bluetoothtictoegame.bluetooth_manger

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import com.example.bluetoothtictoegame.util.Resource
import kotlinx.coroutines.flow.StateFlow


interface TicToeBluetoothManger {

    fun shouldEnableBluetooth(): Boolean

    fun discoverDevices()
    fun cancelDiscovery()
    fun getAlreadyPairedDevices(): List<BluetoothDevice>

    suspend fun connectToHostingDevice(hostingDevice: BluetoothDevice): Resource<BluetoothSocket>

    val serverAcceptState: StateFlow<Resource<BluetoothSocket>>
    suspend fun listenToRequest()
//
//    TODO: add the returned value
//    fun readData()
//
//    TODO: add the parameter of TicTacToeData value
//    fun transmitData()

    fun closeConnection(bluetoothSocket: BluetoothSocket)
}
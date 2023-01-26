package com.example.bluetoothtictoegame.screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import com.example.bluetoothtictoegame.MainActivity

const val enableBluetoothRequestCode = 3

@SuppressLint("MissingPermission")
fun enableBluetooth(activity: MainActivity) {
    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    activity.startActivityForResult(intent, enableBluetoothRequestCode)
}

const val setDeviceDiscoverableRequestCode = 4
const val defaultDiscoverableDuration = 300
@SuppressLint("MissingPermission")
fun makePhoneDiscoverable(activity: MainActivity, discoverableDuration: Int = defaultDiscoverableDuration) {
    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, discoverableDuration)
    activity.startActivityForResult(intent, setDeviceDiscoverableRequestCode)
}
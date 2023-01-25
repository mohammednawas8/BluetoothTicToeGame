package com.example.bluetoothtictoegame.screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import com.example.bluetoothtictoegame.MainActivity

const val activityResultCode = 123

@SuppressLint("MissingPermission")
fun enableBluetooth(context: MainActivity) {
    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    context.startActivityForResult(intent, activityResultCode)
}
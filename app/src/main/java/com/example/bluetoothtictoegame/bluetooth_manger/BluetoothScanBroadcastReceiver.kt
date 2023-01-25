package com.example.bluetoothtictoegame.bluetooth_manger

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private val TAG = "BluetoothScanReceiver"
class BluetoothScanBroadcastReceiver : BroadcastReceiver() {

    private val _devices = MutableLiveData<List<BluetoothDevice>>()
    val devices: LiveData<List<BluetoothDevice>> = _devices

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == BluetoothDevice.ACTION_FOUND) {
            val device: BluetoothDevice? =
                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            device?.let {
                val listOfDevices = _devices.value?.plus(listOf(it))
                _devices.postValue(listOfDevices)
                Log.d(TAG,"Device Found ${it.name} ${it.address}")
            }
        }
    }
}
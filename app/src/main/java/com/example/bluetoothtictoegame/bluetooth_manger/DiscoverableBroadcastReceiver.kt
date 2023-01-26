package com.example.bluetoothtictoegame.bluetooth_manger

import android.bluetooth.BluetoothAdapter.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private val TAG = "DiscoverableReceiver"
class DiscoverableBroadcastReceiver : BroadcastReceiver() {

    private val _discoverableState = MutableLiveData(false)
    val discoverableState: LiveData<Boolean> = _discoverableState

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == ACTION_SCAN_MODE_CHANGED) {
            val currentScanState = intent.getIntExtra(EXTRA_SCAN_MODE, -1)
            if (
                currentScanState == SCAN_MODE_CONNECTABLE_DISCOVERABLE
                || currentScanState == SCAN_MODE_CONNECTABLE
            ) {
                _discoverableState.postValue(true)
                Log.d(TAG,"Device is discoverable")
            } else {
                _discoverableState.postValue(false)
                Log.d(TAG,"Device is not discoverable")
            }
        }
    }

}
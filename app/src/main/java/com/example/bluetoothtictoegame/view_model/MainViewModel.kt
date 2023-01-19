package com.example.bluetoothtictoegame.view_model

import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val packageManager: PackageManager
) : ViewModel() {

    var bluetoothFeature = mutableStateOf(true)
        private set

    init {
        bluetoothFeature.value = checkBluetoothFeature()
    }

    private fun checkBluetoothFeature() =
        packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)

}
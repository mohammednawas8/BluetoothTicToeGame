package com.example.bluetoothtictoegame.screens.permissions_screen.components

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.example.bluetoothtictoegame.screens.permissions_screen.AndroidPhoneVersion

@SuppressLint("InlinedApi")
@Composable
@Stable
fun rememberDevicePermissions(): List<String> {
    val version = AndroidPhoneVersion.getPhoneSdkVersion()
    return when (version) {
        is AndroidPhoneVersion.Sdk31AndAbove -> {
            listOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE,
            )
        }

        //TODO: Access Background Location should be done after the COARSE LOCATION AND FINE LOCATION (Separate Them)
        is AndroidPhoneVersion.Sdk30 -> {
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        }
        is AndroidPhoneVersion.Sdk29 -> {
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        }
        is AndroidPhoneVersion.Sdk28AndLower -> {
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }
}

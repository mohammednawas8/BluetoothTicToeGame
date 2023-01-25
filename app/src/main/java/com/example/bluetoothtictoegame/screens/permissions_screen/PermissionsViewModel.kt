package com.example.bluetoothtictoegame.screens.permissions_screen

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@SuppressLint("InlinedApi")
@HiltViewModel
class PermissionsViewModel @Inject constructor(
    private val packageManager: PackageManager,
) : ViewModel() {

    var bluetoothFeature = mutableStateOf(true)
        private set

    init {
        bluetoothFeature.value = checkBluetoothFeature()
    }


    private fun checkBluetoothFeature() =
        packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)


//    fun getPermissionsToBeRequested(): List<String> {
//        val version = AndroidPhoneVersion.getPhoneSdkVersion()
//        return when (version) {
//            is AndroidPhoneVersion.Sdk31AndAbove -> {
//                listOf(
//                    Manifest.permission.BLUETOOTH_SCAN,
//                    Manifest.permission.BLUETOOTH_CONNECT,
//                    Manifest.permission.BLUETOOTH_ADVERTISE,
//                )
//            }
//            is AndroidPhoneVersion.Sdk30 -> {
//                listOf(
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            }
//            is AndroidPhoneVersion.Sdk29 -> {
//                listOf(
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            }
//            is AndroidPhoneVersion.Sdk28AndLower -> {
//                listOf(
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            }
//        }
//    }

}
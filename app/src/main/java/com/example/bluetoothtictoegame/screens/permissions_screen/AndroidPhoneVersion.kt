package com.example.bluetoothtictoegame.screens.permissions_screen

import android.os.Build

sealed class AndroidPhoneVersion() {
    object Sdk31AndAbove : AndroidPhoneVersion()
    object Sdk30 : AndroidPhoneVersion()
    object Sdk29 : AndroidPhoneVersion()
    object Sdk28AndLower : AndroidPhoneVersion()

    companion object {
        fun getPhoneSdkVersion(): AndroidPhoneVersion {
            return if (Build.VERSION.SDK_INT >= 31)
                Sdk31AndAbove
            else if (Build.VERSION.SDK_INT == 30)
                Sdk30
            else if (Build.VERSION.SDK_INT == 29)
                Sdk29
            else
                Sdk28AndLower
        }
    }
}
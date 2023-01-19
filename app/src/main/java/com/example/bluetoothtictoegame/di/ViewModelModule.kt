package com.example.bluetoothtictoegame.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideBluetoothManger(
        @ApplicationContext context: Context,
    ): BluetoothManager = context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager

        @Provides
        fun provideBluetoothAdapter(
            bluetoothManger: BluetoothManager
        ): BluetoothAdapter? = bluetoothManger.adapter


}
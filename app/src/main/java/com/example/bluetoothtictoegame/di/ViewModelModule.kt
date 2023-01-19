package com.example.bluetoothtictoegame.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.content.pm.PackageManager
import com.example.bluetoothtictoegame.bluetooth_manger.TicToeBluetoothManger
import com.example.bluetoothtictoegame.bluetooth_manger.TicToeBluetoothMangerImplementation
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
    ): BluetoothAdapter = bluetoothManger.adapter

    @Provides
    fun provideTicToeBluetoothManger(
        bluetoothAdapter: BluetoothAdapter
    ): TicToeBluetoothManger = TicToeBluetoothMangerImplementation(bluetoothAdapter)

    @Provides
    fun providePackageManger(
        @ApplicationContext context: Context
    ): PackageManager = context.packageManager
}
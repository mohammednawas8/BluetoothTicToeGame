package com.example.bluetoothtictoegame.view_model

import android.bluetooth.BluetoothServerSocket
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothtictoegame.bluetooth_manger.TicToeBluetoothManger
import com.example.bluetoothtictoegame.screens.client_screen.ClientState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val ticToeBluetoothManger: TicToeBluetoothManger
) : ViewModel() {

    private val _state = MutableStateFlow(ClientState())
    val state = _state.asStateFlow()

    fun discoverDevices() {
        if (_state.value.searchingForHosts)
            return

        viewModelScope.launch { _state.emit(state.value.copy(searchingForHosts = true)) }
        ticToeBluetoothManger.discoverDevices()
        /**
         * Discover devices usually scan for 12 seconds, after 12 seconds update the state.
         */
        viewModelScope.launch {
            repeat(12) {
                delay(1000)
            }
            _state.emit(state.value.copy(searchingForHosts = false))
        }
    }


    fun shouldEnableBluetooth() {
        viewModelScope.launch {
            _state.emit(
                state.value.copy(shouldEnableBluetooth = ticToeBluetoothManger.shouldEnableBluetooth())
            )
        }
    }

    fun connectToServerDevice(serverSocket: BluetoothServerSocket) {

    }

}
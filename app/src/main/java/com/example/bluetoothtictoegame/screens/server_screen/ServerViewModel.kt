package com.example.bluetoothtictoegame.screens.server_screen

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothtictoegame.bluetooth_manger.TicToeBluetoothManger
import com.example.bluetoothtictoegame.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "ServerViewModel"

@HiltViewModel
class ServerViewModel @Inject constructor(
    private val ticToeBluetoothManger: TicToeBluetoothManger
) : ViewModel() {

    private var bluetoothSocket: BluetoothSocket? = null

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _state = MutableStateFlow(ServerState())
    val state = _state.asStateFlow()

    init {
        collectServerStateFromBluetoothManger()
    }


    private fun listenToNearbyPlayer() {
        viewModelScope.launch {
            ticToeBluetoothManger.listenToRequest()
        }
    }

    fun changeDiscoverableState(currentDiscoverableState: Int) {
        if (
            currentDiscoverableState == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE
            || currentDiscoverableState == BluetoothAdapter.SCAN_MODE_CONNECTABLE
        ) {
            viewModelScope.launch {
                _state.emit(_state.value.copy(isPhoneDiscoverable = true))
            }
            listenToNearbyPlayer()
            Log.d(TAG, "Device is discoverable")
        } else {
            viewModelScope.launch {
                _state.emit(_state.value.copy(isPhoneDiscoverable = false))
            }
            Log.d(TAG, "Device is not discoverable")
        }
    }

    private fun collectServerStateFromBluetoothManger() {
        viewModelScope.launch {
            ticToeBluetoothManger.serverAcceptState.collect {
                when (it) {
                    is Resource.Loading -> viewModelScope.launch {
                        _state.emit(
                            _state.value.copy(
                                isListening = true,
                                remoteBluetoothDevice = null
                            )
                        )

                    }
                    is Resource.Success -> {
                        bluetoothSocket = it.data
                        viewModelScope.launch {
                            _state.emit(
                                _state.value.copy(
                                    isListening = false,
                                    remoteBluetoothDevice = bluetoothSocket?.remoteDevice
                                )
                            )
                        }
                    }
                    is Resource.Error -> {
                        viewModelScope.launch {
                            _error.emit(it.error.toString())
                        }
                        viewModelScope.launch {
                            _state.emit(
                                _state.value.copy(
                                    isListening = false,
                                    remoteBluetoothDevice = null
                                )
                            )
                        }
                    }
                    else -> viewModelScope.launch {
                        _state.emit(
                            _state.value.copy(
                                isListening = false,
                                remoteBluetoothDevice = null
                            )
                        )
                    }
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        ticToeBluetoothManger.closeConnection(bluetoothSocket)
    }


}
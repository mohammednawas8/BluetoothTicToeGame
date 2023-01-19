package com.example.bluetoothtictoegame.bluetooth_manger

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.example.bluetoothtictoegame.BuildConfig
import com.example.bluetoothtictoegame.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

private val TAG = "TicToeBluetoothManger"

@SuppressLint("MissingPermission")
class TicToeBluetoothMangerImplementation(
    private val bluetoothAdapter: BluetoothAdapter?,
) : TicToeBluetoothManger {

    private var _serverAcceptState: MutableStateFlow<Resource<BluetoothSocket>> =
        MutableStateFlow(Resource.Nothing())
    override val serverAcceptState = _serverAcceptState.asStateFlow()

    override fun shouldEnableBluetooth(): Boolean {
        if (bluetoothAdapter == null)
            return false
        return bluetoothAdapter.isEnabled.not()
    }

    override fun discoverDevices() {
        bluetoothAdapter?.startDiscovery()
    }

    override suspend fun connectToHostingDevice(hostingDevice: BluetoothDevice): Resource<BluetoothSocket> {
        return withContext(Dispatchers.IO) {
            val bluetoothUUID = UUID.fromString(BuildConfig.BLUETOOTH_UUID)
            val clientSocket: BluetoothSocket? by lazy {
                hostingDevice.createRfcommSocketToServiceRecord(bluetoothUUID)
            }
            cancelDiscovery()
            if (clientSocket == null)
                Resource.Error("You have to install the game on the hosting phone")
            else {
                clientSocket!!.connect()
                Resource.Success(clientSocket!!)
            }
        }
    }

    override fun cancelDiscovery() {
        bluetoothAdapter?.cancelDiscovery()
    }

    override fun closeConnection(bluetoothSocket: BluetoothSocket) {
        bluetoothSocket.close()
    }

    /**
     * Keep listening to coming requests using a while loop, and in each iteration call [accept] on the serverSocket.
     * if the [accept] function returns a socket, in this case it's the hosting device socket.
     * if it returns null we don't do anything until we catch an exception
     */
    override suspend fun listenToRequest() {
        val bluetoothUUID = UUID.fromString(BuildConfig.BLUETOOTH_UUID)
        val serverSocket: BluetoothServerSocket? by lazy {
            bluetoothAdapter?.listenUsingRfcommWithServiceRecord("TicToe", bluetoothUUID)
        }
        _serverAcceptState.emit(Resource.Loading())
        withContext(Dispatchers.IO) {
            var shouldListenForComingRequests = true
            while (shouldListenForComingRequests) {
                val hostingDeviceSocket: BluetoothSocket? = try {
                    serverSocket?.accept()
                } catch (e: IOException) {
                    Log.e(TAG, "Error in listeningToRequest()", e)
                    _serverAcceptState.emit(Resource.Error(e.message.toString()))
                    shouldListenForComingRequests = false
                    null
                }
                /**
                 * When [serverDeviceSocket] is not null that means we are connected with a device.
                 */
                hostingDeviceSocket?.let {
                    _serverAcceptState.emit(Resource.Success(it))
                    shouldListenForComingRequests = false
                    serverSocket?.close() //To stop listening for requests
                }
            }
        }
    }
}














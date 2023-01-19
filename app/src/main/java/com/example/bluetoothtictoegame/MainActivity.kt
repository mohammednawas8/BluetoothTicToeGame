package com.example.bluetoothtictoegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bluetoothtictoegame.ui.theme.BluetoothTicToeGameTheme
import com.example.bluetoothtictoegame.view_model.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val bluetoothFeature = mainViewModel.bluetoothFeature.value
            val bluetoothPermissions = rememberMultiplePermissionsState(permissions = emptyList())

            BluetoothTicToeGameTheme {
                Box(
                    modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)) {

                    if (!bluetoothFeature) {

                    } else {

                    }
                }
            }
        }
    }
}

@Composable
fun DeviceWithoutBluetooth() {

}



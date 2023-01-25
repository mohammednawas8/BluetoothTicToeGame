package com.example.bluetoothtictoegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bluetoothtictoegame.screens.Navigation
import com.example.bluetoothtictoegame.screens.client_screen.ClientScreen
import com.example.bluetoothtictoegame.screens.home_screen.HomeScreen
import com.example.bluetoothtictoegame.screens.permissions_screen.PermissionsScreen
import com.example.bluetoothtictoegame.ui.theme.BluetoothTicToeGameTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BluetoothTicToeGameTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Navigation.PermissionsScreen.rout,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(Navigation.PermissionsScreen.rout) {
                            PermissionsScreen(navController = navController)
                        }
                        composable(Navigation.HomeScreen.rout) {
                            HomeScreen(navController = navController)
                        }
                        composable(Navigation.ClientScreen.rout) {
                            ClientScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }


}



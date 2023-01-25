package com.example.bluetoothtictoegame.screens.home_screen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bluetoothtictoegame.R
import com.example.bluetoothtictoegame.screens.Navigation
import com.example.bluetoothtictoegame.screens.permissions_screen.PermissionsScreen
import com.example.bluetoothtictoegame.ui.theme.BluetoothTicToeGameTheme

@Composable
fun HomeScreen(
    navController: NavController
) {
    Log.d("Test", "test")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            HomeScreenHeader()

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                OutlinedButton(
                    onClick = { navController.navigate(Navigation.ClientScreen.rout) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Text(text = "Search Hosting Player", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedButton(
                    onClick = { navController.navigate(Navigation.ServerScreen.rout) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Text(text = "Create a Lobby", fontSize = 16.sp)
                }
            }
        }
    }

}

@Composable
private fun HomeScreenHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.game_icon),
            contentDescription = "Bluetooth Icon",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Bluetooth Tic Toe \n Game",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
        )
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewHomeScreen() {
    BluetoothTicToeGameTheme {
        HomeScreen(rememberNavController())
    }
}
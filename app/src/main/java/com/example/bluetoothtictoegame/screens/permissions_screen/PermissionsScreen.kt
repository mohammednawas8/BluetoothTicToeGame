package com.example.bluetoothtictoegame.screens.permissions_screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.bluetoothtictoegame.screens.Navigation
import com.example.bluetoothtictoegame.screens.permissions_screen.components.PermissionRationalDialog
import com.example.bluetoothtictoegame.screens.permissions_screen.components.TicToeButton
import com.example.bluetoothtictoegame.screens.permissions_screen.components.rememberDevicePermissions
import com.example.bluetoothtictoegame.ui.theme.BluetoothTicToeGameTheme
import com.google.accompanist.permissions.*
import kotlin.random.Random

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsScreen(
    navController: NavController,
) {

    val permissionsState =
        rememberMultiplePermissionsState(permissions = rememberDevicePermissions())

    var rationalDialogState: RationalDialogState? by remember {
        mutableStateOf(null)
    }

    var launchEffectState by remember {
        mutableStateOf(0)
    }

    val context = LocalContext.current

    /**
     * Ask for the permissions, and since asking for permissions is not compose related code it should be handled in a side effect
     */
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                if (!permissionsState.shouldShowRationale)
                    permissionsState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        rationalDialogState?.let {
            PermissionRationalDialog(
                title = it.title,
                message = it.message,
                permissionState = it.permissionState,
                onConfirmClick = {
                    permissionsState.launchMultiplePermissionRequest()
                    rationalDialogState = null
                },
                modifier = Modifier.height(180.dp)
            ) {
                rationalDialogState = null
            }
        }

        /**
         * Determine to which permission we should show the Rational dialog
         */
        LaunchedEffect(key1 = launchEffectState) {
            permissionsState.permissions.forEach { perm ->
                val permissionState = perm.status
                if (permissionState.shouldShowRationale) {
                    when (perm.permission) {
                        Manifest.permission.BLUETOOTH_SCAN -> {
                            rationalDialogState = RationalDialogState(
                                "Permission Required",
                                "Scan permission is required to search for hosting player.",
                                perm
                            )
                        }
                        Manifest.permission.BLUETOOTH_ADVERTISE -> {
                            rationalDialogState = RationalDialogState(
                                "Permission Required",
                                "Advertise permission is required to make your phone discoverable for other player.",
                                perm
                            )
                        }
                        Manifest.permission.BLUETOOTH_CONNECT -> {
                            rationalDialogState = RationalDialogState(
                                "Permission Required",
                                "Connect permission is required to connect with other player.",
                                perm
                            )
                        }
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION -> {
                            rationalDialogState = RationalDialogState(
                                "Permission Required",
                                "Background Location is required to play this game with other player.",
                                perm
                            )
                        }
                    }
                }
            }
        }


        TextAndButtonSction(permissionsState) {
            val arePermissionsDeniedForEver =
                permissionsState.revokedPermissions.isNotEmpty() && !permissionsState.shouldShowRationale
            if (arePermissionsDeniedForEver) {
                Toast.makeText(context, "Enable permissions from app settings", Toast.LENGTH_SHORT)
                    .show()
            } else {
                launchEffectState += 1
            }
        }
    }

    if (permissionsState.allPermissionsGranted) {
        LaunchedEffect(key1 = true) {
            navController.navigate(Navigation.HomeScreen.rout,){
                navController.popBackStack()
            }
        }
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun TextAndButtonSction(
    permissionsState: MultiplePermissionsState,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if (!permissionsState.allPermissionsGranted) {
            Text(text = "Permissions Required", color = MaterialTheme.colors.onBackground)
            Spacer(modifier = Modifier.height(10.dp))
            TicToeButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Allow permissions"
            ) {
                onClick()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
data class RationalDialogState(
    val title: String,
    val message: String,
    val permissionState: PermissionState
)


@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewPermissionsScreen() {
    BluetoothTicToeGameTheme {
        PermissionsScreen(navController = rememberNavController())
    }
}



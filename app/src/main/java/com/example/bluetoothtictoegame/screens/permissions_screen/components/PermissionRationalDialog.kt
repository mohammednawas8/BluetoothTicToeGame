package com.example.bluetoothtictoegame.screens.permissions_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluetoothtictoegame.ui.theme.BluetoothTicToeGameTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRationalDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    permissionState: PermissionState? = null,
    onConfirmClick: (PermissionState?) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(10.dp),
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface,
            )
        },
        text = {
            Text(
                text = message,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirmClick(permissionState) }) {
                Text(text = "Allow", color = MaterialTheme.colors.onSurface)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Deny", color = MaterialTheme.colors.onSurface)
            }
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,showBackground = true, name = "Night Mode")
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PermissionRationalDialogPreview() {
    BluetoothTicToeGameTheme {
        PermissionRationalDialog(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            title = "Permission required",
            message = "You have to enable the scan permission to scan for devices.",
            onConfirmClick = { /*TODO*/ }) {
        }
    }
}

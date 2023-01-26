package com.example.bluetoothtictoegame.screens.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetoothtictoegame.ui.theme.BluetoothTicToeGameTheme

@Composable
fun TicToeTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    action: String,
    isActionLoading: Boolean,
    actionClick: (() -> Unit)? = null
) {
    TopAppBar(modifier = modifier, backgroundColor = MaterialTheme.colors.primarySurface) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )

            if (isActionLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(30.dp))
            } else {
                TextButton(onClick = {
                    if (actionClick != null) {
                        actionClick()
                    }
                }) {
                    Text(text = action, color = Color.White, fontSize = 14.sp)
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTicToeAppBar() {
    BluetoothTicToeGameTheme {
        TicToeTopAppBar(
            title = "Search Players",
            action = "Search",
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            isActionLoading = false
        ) {

        }
    }
}










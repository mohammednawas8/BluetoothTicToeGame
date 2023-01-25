package com.example.bluetoothtictoegame.screens

sealed class Navigation(val rout: String) {
    object PermissionsScreen: Navigation("permissionsScreen")
    object HomeScreen: Navigation("homeScreen")
    object ClientScreen : Navigation("clientScreen")
    object ServerScreen : Navigation("serverScreen")
    object GameBoardScreen : Navigation("gameBoardScreen")
}
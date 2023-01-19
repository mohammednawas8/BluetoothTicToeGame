package com.example.bluetoothtictoegame.util

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String) : Resource<T>(error = message)
    class Loading<T>() : Resource<T>()
    class Nothing<T>() : Resource<T>()
}
package com.example.cryptocoinstest.data.network

sealed class NetworkState<out T> {
    object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val message: String) : NetworkState<Nothing>()
}
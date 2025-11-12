package com.example.cryptocoinstest.data.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Tarun Gupta on 2025-11-11
 *
 * Common Class supporting to provide network related information
 * Information providing:
 * isNetworkAvailable: Method to check if device is connected to any network
 *
 */
@Singleton
class NetworkHelper @Inject constructor(
    private val connectivityManager: ConnectivityManager
) {
    fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
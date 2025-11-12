package com.example.cryptocoinstest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocoinstest.data.network.NetworkState
import com.example.cryptocoinstest.domain.GetCryptosUseCase
import com.example.cryptocoinstest.domain.model.CryptoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Tarun Gupta on 2025-11-11
 *
 * CryptoViewModel helps to interact UI with datasource.
 * ViewModel fetches the data from the datasource, stores it and manages the state of UI on the basis of response.
 *
 */

class CryptoViewModel @Inject constructor(
    private val cryptosUseCase: GetCryptosUseCase
): ViewModel() {

    private val _cryptoState = MutableStateFlow<NetworkState<List<CryptoResponse>>>(NetworkState.Loading)
    val cryptoState = _cryptoState.asStateFlow()

    fun fetchCryptoList() {
        viewModelScope.launch {
            _cryptoState.value = NetworkState.Loading
            _cryptoState.value = cryptosUseCase.fetchCryptoList()
        }
    }
}
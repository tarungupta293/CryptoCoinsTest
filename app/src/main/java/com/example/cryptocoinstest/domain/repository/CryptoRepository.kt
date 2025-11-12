package com.example.cryptocoinstest.domain.repository

import com.example.cryptocoinstest.data.network.NetworkState
import com.example.cryptocoinstest.domain.model.CryptoResponse

/**
 * Created by Tarun Gupta on 2025-11-11
 *
 * Part of domain layer where Repositories and encapsulated and provides only business logic.
 * This repository provides information of api fetching cryptos list.
 *
 */
interface CryptoRepository {
    suspend fun getCryptoList() : NetworkState<List<CryptoResponse>>
}
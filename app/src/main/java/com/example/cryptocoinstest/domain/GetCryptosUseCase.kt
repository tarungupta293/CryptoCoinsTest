package com.example.cryptocoinstest.domain

import com.example.cryptocoinstest.data.network.NetworkState
import com.example.cryptocoinstest.domain.model.CryptoResponse
import com.example.cryptocoinstest.domain.repository.CryptoRepository
import javax.inject.Inject

/**
 * Created by Tarun Gupta on 2025-11-11
 *
 * UseCases: Part of domain layer
 * Provides information of independent feature. Here, this Usecase helps to fetch CryptoList.
 * This usecase will call to the Repository for data from the datasource
 *
 */
class GetCryptosUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository
) {
    suspend fun fetchCryptoList() : NetworkState<List<CryptoResponse>> {
        return cryptoRepository.getCryptoList()
    }
}
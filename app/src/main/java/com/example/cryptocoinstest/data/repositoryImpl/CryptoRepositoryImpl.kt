package com.example.cryptocoinstest.data.repositoryImpl

import com.example.cryptocoinstest.data.cache.CryptoCache
import com.example.cryptocoinstest.data.network.ApiInterface
import com.example.cryptocoinstest.data.network.NetworkHelper
import com.example.cryptocoinstest.data.network.NetworkState
import com.example.cryptocoinstest.domain.model.CryptoResponse
import com.example.cryptocoinstest.domain.repository.CryptoRepository
import javax.inject.Inject

/**
 * Created by Tarun Gupta on 2025-11-11
 *
 * Repository Implementation to extends the functionality of Repository
 * Here, we have implemented CryptoRepository which is providing the list of crypto coins.
 * Parameters needed:
 * ApiInterface: to call cryptoList api.
 * CryptoCache: helps to cache the online coins list information
 * NetworkHelper: can help to check if user's device is online
 */

class CryptoRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val cryptoCache: CryptoCache,
    private val networkHelper: NetworkHelper
) : CryptoRepository {
    override suspend fun getCryptoList(): NetworkState<List<CryptoResponse>> {
        return if (networkHelper.isNetworkAvailable()) {
            val response = apiInterface.getCoinsList("CG-vyz5JBbPDDapGmgsMyYHuvis", "AUD", "5")
            try{
                val cryptoList = response.body()
                if (!cryptoList.isNullOrEmpty()) {
                    cryptoCache.saveCoins(cryptoList)
                    NetworkState.Success(cryptoList)
                } else {
                    NetworkState.Success(cryptoCache.getCoins()?: emptyList())
                }
            } catch (ex: Exception) {
                NetworkState.Error("Failed to load data: ${ex.message}")
            }

        } else {
            NetworkState.Success(cryptoCache.getCoins()?: emptyList())
        }
    }

}
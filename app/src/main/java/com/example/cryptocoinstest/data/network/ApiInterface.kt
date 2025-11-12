package com.example.cryptocoinstest.data.network

import com.example.cryptocoinstest.domain.model.CryptoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("coins/markets?")
    suspend fun getCoinsList(
        @Header("api_key") apiKey: String,
        @Query("vs_currency") currency: String,
        @Query("per_page") page: String) : Response<List<CryptoResponse>>
}
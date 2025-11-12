package com.example.cryptocoinstest.data.cache

import com.example.cryptocoinstest.domain.model.CryptoResponse
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.days

/**
 * Created by Tarun Gupta on 2025-11-11
 *
 * Crypto4K library used for CryptoCache.
 * Class caches the data and retains it when user offline.
 * {@CryptoCache} class survives the data till 1 day of saved timestamp.
 *
 */
class CryptoCache {

    private val cache: Cache<String, List<CryptoResponse>> = Cache.Builder()
        .expireAfterWrite(1.days)
        .build()

    private val mutex = Mutex()

    suspend fun getCoins(): List<CryptoResponse>? = mutex.withLock { cache.get("coins") }

    suspend fun saveCoins(coins: List<CryptoResponse>) {
        mutex.withLock { cache.put("coins", coins) }
    }
}
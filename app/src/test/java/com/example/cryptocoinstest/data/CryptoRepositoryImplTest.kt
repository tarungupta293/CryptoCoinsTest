package com.example.cryptocoinstest.data

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.cryptocoinstest.data.cache.CryptoCache
import com.example.cryptocoinstest.data.network.ApiInterface
import com.example.cryptocoinstest.data.network.NetworkHelper
import com.example.cryptocoinstest.data.network.NetworkState
import com.example.cryptocoinstest.data.repositoryImpl.CryptoRepositoryImpl
import com.example.cryptocoinstest.domain.model.CryptoResponse
import com.example.cryptocoinstest.domain.repository.CryptoRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class CryptoRepositoryImplTest {

    @Mock private lateinit var apiService: ApiInterface
    private lateinit var cache: CryptoCache
    @Mock private lateinit var connectivityManager: ConnectivityManager
    @Mock private lateinit var networkInfo: NetworkInfo
    private lateinit var repository: CryptoRepository
    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this)

//        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
//        `when`(networkInfo.isConnected).thenReturn(true)  // Simulating network connection (change to false for offline)

        // Create a real instance of NetworkHelper
        networkHelper = mock(NetworkHelper::class.java)
        cache = CryptoCache()
        repository = CryptoRepositoryImpl(apiService, cache, networkHelper)
    }

    @Test
    fun `fetch coins success from API when connected to the network`() = runBlocking {
        // Mock the response from the API
        val crypto = listOf(CryptoResponse("1", "Crypto Test", "Crypto Test"))
        val response = Response.success(crypto)
        `when`(apiService.getCoinsList("CG-vyz5JBbPDDapGmgsMyYHuvis", "AUD", "5")).thenReturn(response)
        `when`(networkHelper.isNetworkAvailable()).thenReturn(true)
        // Call the repository method
        val result = repository.getCryptoList()

        // Assert that the result matches the mock response
        assertEquals(crypto, (result as NetworkState.Success).data)
    }

    @Test
    fun `fetch coins from cache when not connected to the network`() = runBlocking {
        // Simulate no network connection
        `when`(networkHelper.isNetworkAvailable()).thenReturn(false)

        // Pre-fill the cache with some data
        val crypto = listOf(CryptoResponse("1", "Crypto Test 2", "Crypto Test 2"))
        cache.saveCoins(crypto)

        // Call the repository method (it should fetch data from cache due to offline mode)
        val result = repository.getCryptoList()

        // Assert that the result is from the cache
        assertEquals(crypto, (result as NetworkState.Success).data)
    }

}
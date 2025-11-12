package com.example.cryptocoinstest.ui

import com.example.cryptocoinstest.data.network.NetworkState
import com.example.cryptocoinstest.domain.model.CryptoResponse
import com.example.cryptocoinstest.domain.GetCryptosUseCase
import com.example.cryptocoinstest.ui.viewmodel.CryptoViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class CryptoViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule() // Custom rule for coroutines

    private lateinit var viewModel: CryptoViewModel

    @Mock
    private lateinit var usecase: GetCryptosUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CryptoViewModel(usecase)
    }

    @Test
    fun `get coins success`() = runBlocking {
        val mockCrypto = listOf(CryptoResponse("1", "Crypto Test 3", "Crypto Test 3"))
        Mockito.`when`(usecase.fetchCryptoList()).thenReturn(NetworkState.Success(mockCrypto))

        viewModel.fetchCryptoList()

        val result = viewModel.cryptoState.value
        assert(result is NetworkState.Success<List<CryptoResponse>>)
        assertEquals((result as NetworkState.Success<List<CryptoResponse>>).data[0].name, "Crypto Test 3")
    }
}
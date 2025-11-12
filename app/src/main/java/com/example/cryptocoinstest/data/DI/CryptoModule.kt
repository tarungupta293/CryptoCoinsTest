package com.example.cryptocoinstest.data.DI

import android.content.Context
import android.net.ConnectivityManager
import com.example.cryptocoinstest.data.cache.CryptoCache
import com.example.cryptocoinstest.data.network.ApiInterface
import com.example.cryptocoinstest.data.network.NetworkHelper
import com.example.cryptocoinstest.data.repositoryImpl.CryptoRepositoryImpl
import com.example.cryptocoinstest.domain.GetCryptosUseCase
import com.example.cryptocoinstest.domain.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Tarun Gupta on 2025-11-11
 *
 * Common Module class for DI providers.
 * All providers provided throughout application are initialised here
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {

    /**
     * This method provides Retrofit service for the apis.
     * This is a singleton method and can be used without class object
     */
    @Provides
    @Singleton
    fun providesApiService() : ApiInterface {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    /**
     * This method initialises the CryptoUsecase class object
     * ViewModel use this usecase object
     * parameters needed for this usecase is CryptoRepository
     */
    @Provides
    @Singleton
    fun providesCryptoUseCase(cryptoRepository: CryptoRepository): GetCryptosUseCase {
        return GetCryptosUseCase(cryptoRepository)
    }

    /**
     * Method provides ConnectivityManager object to the NetworkHelper class.
     * parameters needed for this method is @ApplicationContext
     */
    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /**
     * provides NetworkHelper object.
     * parameters needed for this method is @ConnectivityManager
     */
    @Provides
    @Singleton
    fun provideNetworkHelper(
        connectivityManager: ConnectivityManager
    ): NetworkHelper {
        return NetworkHelper(connectivityManager)
    }

    /**
     * provides @{CryptoCache} class object
     */
    @Provides
    @Singleton
    fun provideCryptoCache(): CryptoCache {
        return CryptoCache()
    }

    /**
     * provides CryptoRepository class object to the UseCases.
     * Parameters needed:
     * ApiInterface: where apis are written
     * CryptoCache: Cache class object
     * NetworkHelper: provides network related information
     */
    @Provides
    @Singleton
    fun providesCryptoRepository(apiInterface: ApiInterface,
                                 cryptoCache: CryptoCache,
                                 networkHelper: NetworkHelper): CryptoRepository {
        return CryptoRepositoryImpl(apiInterface, cryptoCache, networkHelper)
    }
}
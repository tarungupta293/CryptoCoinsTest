package com.example.cryptocoinstest.domain.model

data class CryptoResponse(
    val id: String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val image: String? = null,
    val current_price: Double? = null,
    val market_cap: Long? = null,
    val market_cap_rank: Int? = null,
    val fully_diluted_valuation: Long? = null,
    val total_volume: Long? = null,
    val high_24h: Double? = null,
    val low_24h: Double? = null,
    val price_change_24h:Double? = null,
    val price_change_percentage_24h:Double? = null,
    val market_cap_change_24h:Double? = null,
    val market_cap_change_percentage_24h:Double? = null,
    val circulating_supply:Double? = null,
    val total_supply:Double? = null,
    val max_supply: Long? = null,
    val ath:Double? = null,
    val ath_change_percentage:Double? = null,
    val ath_date: String? = null,
    val atl:Double? = null,
    val atl_change_percentage:Double? = null,
    val atl_date: String? = null,
    val last_updated: String? = null
)

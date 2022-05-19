package com.kareemdev.latihanpagingnetwork.data

import com.kareemdev.latihanpagingnetwork.database.QuoteDatabase
import com.kareemdev.latihanpagingnetwork.network.ApiService
import com.kareemdev.latihanpagingnetwork.network.QuoteResponseItem

class QuoteRepository(private val quoteDatabase: QuoteDatabase, private val apiService: ApiService) {
    suspend fun getQuote(): List<QuoteResponseItem> {
        return apiService.getQuote(1, 5)
    }
}
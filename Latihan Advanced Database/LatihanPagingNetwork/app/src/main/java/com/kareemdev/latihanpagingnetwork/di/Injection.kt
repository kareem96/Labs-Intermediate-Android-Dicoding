package com.kareemdev.latihanpagingnetwork.di

import android.content.Context
import com.kareemdev.latihanpagingnetwork.data.QuoteRepository
import com.kareemdev.latihanpagingnetwork.database.QuoteDatabase
import com.kareemdev.latihanpagingnetwork.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): QuoteRepository {
        val database = QuoteDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return QuoteRepository(database, apiService)
    }
}
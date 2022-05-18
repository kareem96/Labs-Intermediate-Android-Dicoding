package com.kareemdev.latihanadvancedtesting.di

import android.content.Context
import com.kareemdev.latihanadvancedtesting.data.NewsRepository
import com.kareemdev.latihanadvancedtesting.data.local.room.NewsDatabase
import com.kareemdev.latihanadvancedtesting.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository{
        val apiService =ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}
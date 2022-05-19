package com.kareemdev.latihanadvancedtesting.data

import com.kareemdev.latihanadvancedtesting.data.remote.response.NewsResponse
import com.kareemdev.latihanadvancedtesting.data.remote.retrofit.ApiService
import com.kareemdev.latihanadvancedtesting.DataDummy

class FakeApiService: ApiService {
    private val dummyResponse = DataDummy.generateDummyNewsResponse()
    override suspend fun getNews(apiKey: String): NewsResponse {
        return dummyResponse
    }
}
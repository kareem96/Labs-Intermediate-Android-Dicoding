package com.kareemdev.latihanadvancedtesting.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kareemdev.latihanadvancedtesting.BuildConfig
import com.kareemdev.latihanadvancedtesting.data.local.entity.NewsEntity
import com.kareemdev.latihanadvancedtesting.data.local.room.NewsDao
import com.kareemdev.latihanadvancedtesting.data.remote.retrofit.ApiService

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
){
    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews(BuildConfig.API_KEY)
            val articles = response.articles
            val newsList = articles.map { article ->
                NewsEntity(
                    article.title,
                    article.publishedAt,
                    article.urlToImage,
                    article.url
                )
            }
            emit(Result.Success(newsList))
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }
    suspend fun saveNews(news: NewsEntity) {
        newsDao.saveNews(news)
    }
    fun isNewsBookmarked(title: String): LiveData<Boolean> {
        return newsDao.isNewsBookmarked(title)
    }
    suspend fun deleteNews(title: String) {
        newsDao.deleteNews(title)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao)
            }.also { instance = it }
    }
}
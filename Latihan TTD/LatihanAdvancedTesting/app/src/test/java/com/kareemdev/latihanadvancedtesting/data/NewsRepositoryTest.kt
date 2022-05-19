package com.kareemdev.latihanadvancedtesting.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kareemdev.latihanadvancedtesting.MainCoroutineRule
import com.kareemdev.latihanadvancedtesting.data.local.room.NewsDao
import com.kareemdev.latihanadvancedtesting.data.remote.retrofit.ApiService
import com.kareemdev.latihanadvancedtesting.getOrAwaitValue
import com.kareemdev.latihanadvancedtesting.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsRepositoryTest{
    @get:Rule
    var isntanceExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var apiService: ApiService
    private lateinit var newsDao: NewsDao
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp(){
        apiService = FakeApiService()
        newsDao = FakeNewsDao()
        newsRepository = NewsRepository(apiService, newsDao)
    }

    @Test
    fun `when getNews Should Not Null`() = mainCoroutineRule.runBlockingTest {
        val expectedNews = DataDummy.generateDummyNewsResponse()
        val actualNews = apiService.getNews("apiKey")
        Assert.assertNotNull(actualNews)
        Assert.assertEquals(expectedNews.articles.size, actualNews.articles.size)
    }
    @Test
    fun `when saveNews Should Exist in getBookmarkedNews`() = mainCoroutineRule.runBlockingTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsDao.saveNews(sampleNews)
        val actualNews = newsDao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertTrue(actualNews.contains(sampleNews))
        Assert.assertTrue(newsDao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }
    @Test
    fun `when deleteNews Should Not Exist in getBookmarkedNews`() = mainCoroutineRule.runBlockingTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsDao.saveNews(sampleNews)
        newsDao.deleteNews(sampleNews.title)
        val actualNews = newsDao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertFalse(actualNews.contains(sampleNews))
        Assert.assertFalse(newsDao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }
}
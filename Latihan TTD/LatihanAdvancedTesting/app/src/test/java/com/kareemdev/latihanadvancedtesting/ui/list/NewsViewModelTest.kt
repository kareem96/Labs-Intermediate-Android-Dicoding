package com.kareemdev.latihanadvancedtesting.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kareemdev.latihanadvancedtesting.data.NewsRepository
import com.kareemdev.latihanadvancedtesting.data.local.entity.NewsEntity
import com.kareemdev.latihanadvancedtesting.utils.DataDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import com.kareemdev.latihanadvancedtesting.data.Result
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var viewModel: NewsViewModel
    private val dummyNews =  DataDummy.generateDummyNewsEntity()
    @Before
    fun setUp(){
        viewModel = NewsViewModel(newsRepository)
    }
    @Test
    fun `when Get headlinesNews Should Not null and Return Success`(){
        val observer = Observer<Result<List<NewsEntity>>>{}
        try {
            val expectedNews = MutableLiveData<Result<List<NewsEntity>>>()
            expectedNews.value = Result.Success(dummyNews)
            `when`(viewModel.getHeadlineNews()).thenReturn(expectedNews)
            val actualNews = viewModel.getHeadlineNews()
            Assert.assertNotNull(actualNews)
        }finally {
            viewModel.getHeadlineNews().removeObserver(observer)
        }
    }
}
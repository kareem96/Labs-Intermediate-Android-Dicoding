package com.kareemdev.latihanadvancedtesting.ui.list

import androidx.lifecycle.ViewModel
import com.kareemdev.latihanadvancedtesting.data.NewsRepository

class NewsViewModel (private val newsRepository: NewsRepository): ViewModel(){
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()
}
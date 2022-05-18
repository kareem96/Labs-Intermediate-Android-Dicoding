package com.kareemdev.latihanadvancedtesting.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kareemdev.latihanadvancedtesting.data.NewsRepository
import com.kareemdev.latihanadvancedtesting.di.Injection
import com.kareemdev.latihanadvancedtesting.ui.detail.NewsDetailViewModel
import com.kareemdev.latihanadvancedtesting.ui.list.NewsViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val newsRepository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        }else if(modelClass.isAssignableFrom(NewsDetailViewModel::class.java)){
            return NewsDetailViewModel(newsRepository) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class:" + modelClass.name)
    }
}
package com.kareemdev.dicodingstory.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.kareemdev.dicodingstory.data.model.ListStoryItem
import com.kareemdev.dicodingstory.data.remote.ApiService

class Repository (private val apiService: ApiService){
    fun getListStory(): LiveData<PagingData<ListStoryItem>>{
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {StoryPagingSource(apiService)}
        ).liveData
    }
}
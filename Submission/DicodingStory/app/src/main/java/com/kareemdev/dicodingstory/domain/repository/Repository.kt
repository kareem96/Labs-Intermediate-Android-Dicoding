package com.kareemdev.dicodingstory.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.kareemdev.dicodingstory.data.StoryPagingSource
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import com.kareemdev.dicodingstory.data.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository (private val apiService: ApiService){
    fun getStories(location: String?) = apiService.getStories(location)

    fun getListStory(location: String?, page: Int?, size: Int?)= apiService.getStories(location)

    fun postStory(
        description: RequestBody,
        file: MultipartBody.Part,

    ) = apiService.postStory(description, file)
}
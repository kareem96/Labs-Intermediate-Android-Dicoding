package com.kareemdev.dicodingstory.domain.repository

import com.kareemdev.dicodingstory.data.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository (private val apiService: ApiService){
    fun getStories(location: String?) = apiService.getStories(location)

    suspend fun getListStory(
        location: String?,
        page: Int?,
        size: Int?)= apiService.getListStories(location, page, size)

    fun postStory(
        description: RequestBody,
        file: MultipartBody.Part,
        lat: RequestBody?,
        lon: RequestBody?

    ) = apiService.postStory(description, lat, lon, file)
}
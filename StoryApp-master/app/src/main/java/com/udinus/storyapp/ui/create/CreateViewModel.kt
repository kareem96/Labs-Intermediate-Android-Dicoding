package com.udinus.storyapp.ui.create

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.udinus.storyapp.data.remote.response.ResponseUpload
import com.udinus.storyapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class CreateViewModel @Inject constructor(
    private val repository: Repository
):ViewModel(){
    fun getToken(): Flow<String?> = repository.getToken()

    suspend fun uploadImage(
        token:String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?,
    ):Flow<Result<ResponseUpload>> = repository.uploadStory(token, file, description, lat, lon)
}
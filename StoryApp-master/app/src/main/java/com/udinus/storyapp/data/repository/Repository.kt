package com.udinus.storyapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.udinus.storyapp.data.local.AppPreferences
import com.udinus.storyapp.data.local.entity.Story
import com.udinus.storyapp.data.local.room.StoryDatabase
import com.udinus.storyapp.data.remote.RemoteDataMediator
import com.udinus.storyapp.data.remote.api.ApiService
import com.udinus.storyapp.data.remote.response.LoginResponse
import com.udinus.storyapp.data.remote.response.ResponseRegister
import com.udinus.storyapp.data.remote.response.ResponseUpload
import com.udinus.storyapp.data.remote.response.StoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


@ExperimentalPagingApi
class Repository @Inject constructor(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService,
    private val appPreferences: AppPreferences
){

    private fun generateBearerToken(token: String): String {
        return "Bearer $token"
    }


    suspend fun userLogin(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.userLogin(email, password)
            emit(Result.success(response))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun userRegister(
        name: String,
        email: String,
        password: String
    ): Flow<Result<ResponseRegister>> = flow {
        try {
            val response = apiService.userRegister(name, email, password)
            emit(Result.success(response))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun saveAuthToken(token: String) {
        appPreferences.saveAuthToken(token)
    }

    fun getStories(token: String): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = RemoteDataMediator(storyDatabase, apiService, "Bearer $token"),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }
        ).flow
    }


    suspend fun uploadStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?,
    ): Flow<Result<ResponseUpload>> = flow {
        try {
            val tokenBearer = generateBearerToken(token)
            val response = apiService.uploadImage(tokenBearer, file, description, lat, lon)
            emit(Result.success(response))
        }catch (e:Exception){
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }


    fun getStoriesLocation(token: String): Flow<Result<StoryResponse>> = flow {
        try {
            val userToken = "Bearer $token"
            val response = apiService.getAllStories(userToken, null, size = 100, location = 1)
            emit(Result.success(response))
        }catch (exception: Exception){
            emit(Result.failure(exception))
        }
    }


    fun getToken(): Flow<String?> = appPreferences.getAuthToken()
}
package com.kareemdev.dicodingstory.data.remote

import com.kareemdev.dicodingstory.data.LoginRequest
import com.kareemdev.dicodingstory.data.RegisterRequest
import com.kareemdev.dicodingstory.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("register")
    fun register(
        @Body body: RegisterRequest,
        /*@Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,*/
    ): Call<RegisterResponse>

    @POST("login")
    fun login(
        @Body body: LoginRequest,
        /*@Field("email") email: String,
        @Field("password") password: String,*/
    ): Call<BaseResponse>

    @GET("stories")
    suspend fun getListStories(
        @Query("location") location: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
    ): BaseResponse

    @GET("stories")
    fun getStories(
        @Query("location") location: String?
    ): Call<BaseResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
    ): Call<BaseResponse>
}
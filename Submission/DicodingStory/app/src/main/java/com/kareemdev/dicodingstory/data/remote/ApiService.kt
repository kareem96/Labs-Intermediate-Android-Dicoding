package com.kareemdev.dicodingstory.data.remote

import com.kareemdev.dicodingstory.data.model.AddStoryResponse
import com.kareemdev.dicodingstory.data.model.LoginResponse
import com.kareemdev.dicodingstory.data.model.RegisterResponse
import com.kareemdev.dicodingstory.data.model.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @GET("stories")
    fun getListStories(
        @Header("Authorization") authToken: String,
        @Query("Location") page: Int? = 0,
        @Query("Location") size: Int? = 10,
    ): StoriesResponse

    @GET("stories")
    fun getStoriesLoc(
        @Header("Authorization") authToken: String,
        @Query("Location") location: Int = 1,
    ): Call<StoriesResponse>

    @Multipart
    @POST("stories")
    fun addStory(
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") authToken: String,
    ): Call<AddStoryResponse>
}
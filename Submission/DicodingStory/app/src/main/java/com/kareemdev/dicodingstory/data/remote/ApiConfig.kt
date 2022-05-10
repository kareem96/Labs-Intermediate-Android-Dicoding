package com.kareemdev.dicodingstory.data.remote

import com.kareemdev.dicodingstory.BuildConfig
import com.kareemdev.dicodingstory.data.local.datastore.SessionDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(sessionDataStore: SessionDataStore): ApiService{
            val loggingInterceptor = if(BuildConfig.DEBUG){
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            }else{
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                /*.addInterceptor(loggingInterceptor)*/
                .addInterceptor { req ->
                    runBlocking {
                        val token = sessionDataStore.getToken()
                        if(!token.isNullOrEmpty()){
                            val newReq = req.request().newBuilder()
                                .addHeader("Authorization", "Bearer $token")
                                .build()
                            req.proceed(newReq)
                        }else{
                            req.proceed(req.request())
                        }
                    }
                }
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
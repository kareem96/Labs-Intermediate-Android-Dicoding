package com.kareemdev.dicodingstory.domain.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.kareemdev.dicodingstory.data.StateHandler
import com.kareemdev.dicodingstory.data.local.database.StoryDatabase
import com.kareemdev.dicodingstory.data.model.BaseResponse
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import com.kareemdev.dicodingstory.domain.paging.StoryRemoteMediator
import com.kareemdev.dicodingstory.domain.repository.Repository
import com.kareemdev.dicodingstory.utils.ErrorParser
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import java.io.File

class StoryViewModelImpl(
    database: StoryDatabase,
    storyRepository: Repository,
) : StoryViewModel(database, storyRepository){

    override fun getStories(location: String?) {
        storyViewEvent.postValue(StateHandler.Initial())
        storyViewEvent.postValue(StateHandler.Loading())
        storyRepository.getStories(location).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if(response.isSuccessful){
                    val success = StateHandler.Success(response.body())
                    storyViewEvent.postValue(success)
                    storyViewLast.postValue(success.data?.listStory?.first()?.createdAt)
                }else{
                    storyViewEvent.postValue(StateHandler.Error(ErrorParser.parse(response).message ?: "Unknown error"))
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                storyViewEvent.postValue(
                    StateHandler.Error(t.message ?: "Unknown error")
                )
            }

        })
    }

    override fun getListStories(): LiveData<PagingData<ListStoryItem>> {
         @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 3
            ),
            remoteMediator = StoryRemoteMediator(database, storyRepository),
            pagingSourceFactory = {
                database.storyDao().getAllStory()
            }
        ).liveData
    }

    override fun postStory(description: String, location: Location?, photo: File) {
        storyPostEvent.postValue(StateHandler.Loading())
        val latRequestBody = location?.latitude?.toString()?.toRequestBody("text/plain".toMediaType())
        val lonRequestBody = location?.longitude?.toString()?.toRequestBody("text/plain".toMediaType())
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())

        val requestImageFile = photo.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part =
            MultipartBody.Part.createFormData("photo", photo.name, requestImageFile)

        storyRepository.postStory(
            description = descriptionRequestBody,
            file = imageMultipart,
            lat = latRequestBody,
            lon = lonRequestBody,
        )
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful) {
                        storyPostEvent.postValue(StateHandler.Success(response.body()))

                    } else {
                        storyPostEvent.postValue(
                            StateHandler.Error(
                                ErrorParser.parse(response).message ?: "unknown error"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    storyPostEvent.postValue(
                        StateHandler.Error(
                            t.message ?: "unknown error"
                        )
                    )
                }

            })
    }

    override fun resetPostStory() {
        storyPostEvent.postValue(StateHandler.Initial())
    }

    override fun updateStory(): Boolean {
        val last = storyViewEvent.value?.data?.listStory?.first()
        last?.createdAt?.let{
            if(it != storyViewLastState.value?.toString()){
                return true
            }
        }
        return false
    }

}
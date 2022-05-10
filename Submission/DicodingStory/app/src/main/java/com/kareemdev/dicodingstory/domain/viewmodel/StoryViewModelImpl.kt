package com.kareemdev.dicodingstory.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.kareemdev.dicodingstory.data.StateHandler
import com.kareemdev.dicodingstory.data.local.database.StoryDatabase
import com.kareemdev.dicodingstory.data.model.BaseResponse
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import com.kareemdev.dicodingstory.domain.repository.Repository
import com.kareemdev.dicodingstory.utils.ErrorParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        TODO("Not yet implemented")
        /* @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 3
            ),
            remoteMediator = StoryRemoteMediator(database, storyRepository),
            pagingSourceFactory = {
                database.storyDao().getAllStory()
            }
        ).liveData*/
    }

}
package com.kareemdev.dicodingstory.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.kareemdev.dicodingstory.data.StateHandler
import com.kareemdev.dicodingstory.data.local.database.StoryDatabase
import com.kareemdev.dicodingstory.data.model.BaseResponse
import com.kareemdev.dicodingstory.domain.model.ListStoryItem
import com.kareemdev.dicodingstory.domain.repository.Repository

abstract class StoryViewModel(
    val database: StoryDatabase,
    val storyRepository: Repository
): ViewModel(){

    protected val storyViewEvent = MutableLiveData<StateHandler<BaseResponse>>()
    val storyViewState: LiveData<StateHandler<BaseResponse>> = storyViewEvent

    protected val storyPostEvent = MutableLiveData<StateHandler<BaseResponse>>()
    val storyPostState: LiveData<StateHandler<BaseResponse>> = storyPostEvent

    protected val storyViewLast = MutableLiveData<String>()
    val storyViewLastState: LiveData<String> = storyViewLast

    abstract  fun getStories(location:String?)
    abstract fun getListStories(): LiveData<PagingData<ListStoryItem>>

}
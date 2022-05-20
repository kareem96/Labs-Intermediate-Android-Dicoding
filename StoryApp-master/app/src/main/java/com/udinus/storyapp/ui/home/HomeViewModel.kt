package com.udinus.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.udinus.storyapp.data.local.entity.Story
import com.udinus.storyapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,

):ViewModel(){
    fun logout(){
        viewModelScope.launch {
            repository.saveAuthToken("")
        }
    }

    fun getStory(token:String): LiveData<PagingData<Story>> =
        repository.getStories(token).cachedIn(viewModelScope).asLiveData()
}
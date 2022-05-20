package com.udinus.storyapp

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.udinus.storyapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel(){
    fun getAuth(): Flow<String?> = repository.getToken()
}
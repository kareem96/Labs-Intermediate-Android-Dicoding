package com.udinus.storyapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.udinus.storyapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class RegisterViewModel @Inject constructor(private val repository: Repository): ViewModel(){
    suspend fun register(name:String, email:String, password:String) =
        repository.userRegister(name, email, password)
}
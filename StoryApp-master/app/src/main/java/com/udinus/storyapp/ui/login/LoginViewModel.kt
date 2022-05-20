package com.udinus.storyapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.udinus.storyapp.data.remote.response.LoginResponse
import com.udinus.storyapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){
    suspend fun login(email:String, password:String) : Flow<Result<LoginResponse>> =
        repository.userLogin(email, password)

    fun saveToken(token:String){
        viewModelScope.launch {
            repository.saveAuthToken(token)
        }
    }
}
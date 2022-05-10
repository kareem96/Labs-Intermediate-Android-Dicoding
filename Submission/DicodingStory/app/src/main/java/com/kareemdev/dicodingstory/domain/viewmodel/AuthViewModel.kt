package com.kareemdev.dicodingstory.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kareemdev.dicodingstory.data.StateHandler
import com.kareemdev.dicodingstory.data.model.BaseResponse

abstract class AuthViewModel: ViewModel() {
    protected val loginEvent: MutableLiveData<StateHandler<BaseResponse>> = MutableLiveData()
    val loginState: LiveData<StateHandler<BaseResponse>> = loginEvent

    protected val registerEvent: MutableLiveData<StateHandler<BaseResponse>> = MutableLiveData()
    val registerState: LiveData<StateHandler<BaseResponse>> = registerEvent

    abstract fun login(email:String, password: String)
    abstract fun register(email:String, password: String, name:String)
}
package com.kareemdev.dicodingstory.domain.viewmodel

import androidx.lifecycle.viewModelScope
import com.kareemdev.dicodingstory.data.LoginRequest
import com.kareemdev.dicodingstory.data.StateHandler
import com.kareemdev.dicodingstory.data.model.BaseResponse
import com.kareemdev.dicodingstory.domain.repository.AuthRepository
import com.kareemdev.dicodingstory.utils.ErrorParser.Companion.parse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModelImpl(private val authRepository: AuthRepository):AuthViewModel() {
    override fun login(email: String, password: String) {
        loginEvent.postValue(StateHandler.Loading())
        viewModelScope.launch {
            val request = LoginRequest(email, password)
            authRepository.login(request).enqueue(object : Callback<BaseResponse>{
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    response.body().let {
                        body: BaseResponse? ->
                        println(response.message())
                        println(response.body()?.error)
                        if(response.isSuccessful){
                            loginEvent.postValue(StateHandler.Success(body))
                        }else{
                            loginEvent.postValue(StateHandler.Error(parse(response).message))
                        }
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loginEvent.postValue(StateHandler.Error(t.message))
                }

            })
        }
    }

    override fun register(email: String, password: String, name: String) {
        TODO("Not yet implemented")
    }

}
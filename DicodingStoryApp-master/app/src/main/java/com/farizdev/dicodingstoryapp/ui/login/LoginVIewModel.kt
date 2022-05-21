package com.farizdev.dicodingstoryapp.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.farizdev.dicodingstoryapp.data.local.UserPreferences
import com.farizdev.dicodingstoryapp.data.model.LoginResponse
import com.farizdev.dicodingstoryapp.data.model.User
import com.farizdev.dicodingstoryapp.data.remote.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginVIewModel (private val preference: UserPreferences): ViewModel(){


    private val _emailValid = MutableLiveData<Boolean>()
    private val _passwordValid = MutableLiveData<Boolean>()
    private val _loginStatus = MutableLiveData<Boolean>()
    private val _loading = MutableLiveData<Boolean>()

    val emailValid: LiveData<Boolean> = _emailValid
    val passwordValid: LiveData<Boolean> = _passwordValid
    val loginStatus: LiveData<Boolean> = _loginStatus
    val errorMsg = MutableLiveData<String>()
    val loading: LiveData<Boolean> = _loading

    companion object {
        private const val TAG = "LoginViewModel"
    }

    init {
        _emailValid.value = false
        _passwordValid.value = false
        _loginStatus.value = false
        errorMsg.value = ""
        _loading.value = false
    }

    fun getUser(): LiveData<User>{
        return preference.getUser().asLiveData()
    }

    fun login(email:String, password:String){
        _loading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _loading.value = false
                if(response.isSuccessful){
                    val result = response.body()
                    if(result != null){
                        if(!result.error){
                            errorMsg.value = ""
                            _loginStatus.value = true
                            viewModelScope.launch {
                                preference.login(
                                    User(
                                        result.loginResult?.name.toString(),
                                        email,
                                        password,
                                        result.loginResult?.token.toString()
                                    )
                                )
                            }
                        }
                    }
                }else{
                    Log.e(TAG, response.message() )
                    errorMsg.value = response.message()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loading.value = false
                errorMsg.value = t.message
            }

        })
    }

    fun emailStatus(status:Boolean){
        _emailValid.value = status
    }
    fun passwordStatus(status:Boolean){
        _passwordValid.value = status
    }
}
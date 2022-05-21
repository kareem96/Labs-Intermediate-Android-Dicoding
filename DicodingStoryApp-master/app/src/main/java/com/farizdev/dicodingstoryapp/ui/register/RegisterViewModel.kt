package com.farizdev.dicodingstoryapp.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farizdev.dicodingstoryapp.data.model.LoginResponse
import com.farizdev.dicodingstoryapp.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val _emailValid = MutableLiveData<Boolean>()
    private val _passwordValid = MutableLiveData<Boolean>()
    private val _loading = MutableLiveData<Boolean>()

    val emailValid: LiveData<Boolean> = _emailValid
    val passwordValid: LiveData<Boolean> = _passwordValid
    val statusMessage = MutableLiveData<String>()
    val loading: LiveData<Boolean> = _loading
    init {
        _emailValid.value = false
        _passwordValid.value = false
        statusMessage.value = ""
        _loading.value = false
    }
    companion object {
        private const val TAG = "RegisterViewModel"
    }


    fun register(name: String, email: String, password: String) {
        _loading.value = true

        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _loading.value = false

                if (response.isSuccessful) {
                    val result = response.body()

                    if (result != null) {
                        if (!result.error) {
                            statusMessage.value = "success"
                        }
                    }
                } else {
                    Log.e(TAG, response.message())
                    statusMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "Error message: ${t.message}")
                _loading.value = false
                statusMessage.value = t.message
            }
        })
    }

    fun updateEmailStatus(status: Boolean) {
        _emailValid.value = status
    }

    fun updatePasswordStatus(status: Boolean) {
        _passwordValid.value = status
    }
}
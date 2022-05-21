package com.rickyandrean.a2320j2802_submissionintermediate.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.rickyandrean.a2320j2802_submissionintermediate.model.AuthenticationResponse
import com.rickyandrean.a2320j2802_submissionintermediate.model.UserModel
import com.rickyandrean.a2320j2802_submissionintermediate.network.ApiConfig
import com.rickyandrean.a2320j2802_submissionintermediate.storage.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val preference: UserPreference) : ViewModel() {
    private val _emailValid = MutableLiveData<Boolean>()
    private val _passwordValid = MutableLiveData<Boolean>()
    private val _loginStatus = MutableLiveData<Boolean>()
    private val _loading = MutableLiveData<Boolean>()

    val emailValid: LiveData<Boolean> = _emailValid
    val passwordValid: LiveData<Boolean> = _passwordValid
    val loginStatus: LiveData<Boolean> = _loginStatus
    val errorMsg = MutableLiveData<String>()
    val loading: LiveData<Boolean> = _loading

    init {
        _emailValid.value = false
        _passwordValid.value = false
        _loginStatus.value = false
        errorMsg.value = ""
        _loading.value = false
    }

    fun updateEmailStatus(status: Boolean) {
        _emailValid.value = status
    }

    fun updatePasswordStatus(status: Boolean) {
        _passwordValid.value = status
    }

    fun login(email: String, password: String) {
        _loading.value = true

        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<AuthenticationResponse> {
            override fun onResponse(
                call: Call<AuthenticationResponse>,
                response: Response<AuthenticationResponse>
            ) {
                _loading.value = false

                if (response.isSuccessful) {
                    val result = response.body()

                    if (result != null) {
                        if (!result.error) {
                            errorMsg.value = ""
                            _loginStatus.value = true

                            viewModelScope.launch {
                                preference.login(
                                    UserModel(
                                        result.loginResult?.name.toString(),
                                        email,
                                        password,
                                        result.loginResult?.token.toString()
                                    )
                                )
                            }
                        }
                    }
                } else {
                    Log.e(TAG, response.message())
                    errorMsg.value = response.message()
                }
            }

            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                Log.e(TAG, "Error message: ${t.message}")
                _loading.value = false
                errorMsg.value = t.message
            }
        })
    }

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
package com.farizdev.dicodingstoryapp.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.farizdev.dicodingstoryapp.data.local.UserPreferences
import com.farizdev.dicodingstoryapp.data.model.Story
import com.farizdev.dicodingstoryapp.data.model.StoryResponse
import com.farizdev.dicodingstoryapp.data.model.User
import com.farizdev.dicodingstoryapp.data.remote.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preference: UserPreferences) : ViewModel() {
    private val _stories = MutableLiveData<ArrayList<Story>>()
    private val _loading = MutableLiveData<Boolean>()

    val stories: LiveData<ArrayList<Story>> = _stories
    val errorMessage = MutableLiveData<String>()
    val loading: LiveData<Boolean> = _loading

    init {
        errorMessage.value = ""
        _loading.value = false
    }

    fun getStories(token: String) {
        _loading.value = true

        val client = ApiConfig.getApiService().stories("Bearer $token")
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                _loading.value = false

                if (response.isSuccessful) {
                    val result = response.body()

                    if (result != null) {
                        if (result.listStory != null) {
                            errorMessage.value = ""
                            _stories.value = result.listStory!!
                        }
                    }
                } else {
                    Log.e(TAG, "Error message: ${response.message()}")
                    errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e(TAG, "Error message: ${t.message}")
                _loading.value = false
                errorMessage.value = t.message
            }
        })
    }

    fun getUser(): LiveData<User> {
        return preference.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            preference.logout()
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
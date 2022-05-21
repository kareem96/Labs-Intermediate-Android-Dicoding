package com.rickyandrean.a2320j2802_submissionintermediate.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.rickyandrean.a2320j2802_submissionintermediate.model.ListStoryItem
import com.rickyandrean.a2320j2802_submissionintermediate.model.StoryResponse
import com.rickyandrean.a2320j2802_submissionintermediate.model.UserModel
import com.rickyandrean.a2320j2802_submissionintermediate.network.ApiConfig
import com.rickyandrean.a2320j2802_submissionintermediate.storage.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preference: UserPreference) : ViewModel() {
    private val _stories = MutableLiveData<ArrayList<ListStoryItem>>()
    private val _loading = MutableLiveData<Boolean>()

    val stories: LiveData<ArrayList<ListStoryItem>> = _stories
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
                            // Already do null checking but IDE still show red line in code below
                            // So I decide to add non-null asserted
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

    fun getUser(): LiveData<UserModel> {
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
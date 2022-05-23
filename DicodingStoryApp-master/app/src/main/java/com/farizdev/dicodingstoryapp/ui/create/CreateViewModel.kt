package com.farizdev.dicodingstoryapp.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.farizdev.dicodingstoryapp.data.local.UserPreferences
import com.farizdev.dicodingstoryapp.data.model.User

class CreateViewModel(private val preferences: UserPreferences) : ViewModel(){
    var token = ""
    val loading = MutableLiveData<Boolean>()
    init {
        loading.value = false
    }

    fun getUser(): LiveData<User>{
        return preferences.getUser().asLiveData()
    }
}
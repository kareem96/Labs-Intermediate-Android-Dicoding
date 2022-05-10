package com.kareemdev.dicodingstory.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kareemdev.dicodingstory.data.local.datastore.SessionDataStore
import kotlinx.coroutines.launch

class SessionViewModel(private val _sessionPreference: SessionDataStore) : ViewModel(){
    fun saveToken(token:String) {
        viewModelScope.launch {
            _sessionPreference.saveToken(token)
        }
    }
        fun removeToken(){
            viewModelScope.launch {
                _sessionPreference.removeToken()
            }
        }

    fun getRealtimeToken() = _sessionPreference.getRealtimeToken().asLiveData()
}
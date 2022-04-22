package com.kareemdev.propertyanimation.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.propertyanimation.model.UserModel
import com.kareemdev.propertyanimation.model.UserPreference
import kotlinx.coroutines.launch

class SignupViewModel(private val pref:UserPreference) :ViewModel(){
    fun saveUser(user: UserModel){
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}
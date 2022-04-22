package com.kareemdev.propertyanimation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kareemdev.propertyanimation.model.UserPreference
import com.kareemdev.propertyanimation.view.login.LoginViewModel
import com.kareemdev.propertyanimation.view.signup.SignupViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref:UserPreference) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
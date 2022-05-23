package com.farizdev.dicodingstoryapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farizdev.dicodingstoryapp.data.local.UserPreferences
import com.farizdev.dicodingstoryapp.ui.create.CreateViewModel
import com.farizdev.dicodingstoryapp.ui.home.MainViewModel
import com.farizdev.dicodingstoryapp.ui.login.LoginVIewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref: UserPreferences) :ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(pref: UserPreferences): ViewModelFactory{
            if (INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(pref) as T
            modelClass.isAssignableFrom(LoginVIewModel::class.java) -> LoginVIewModel(pref) as T
            modelClass.isAssignableFrom(CreateViewModel::class.java) -> CreateViewModel(pref) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: " + modelClass.name)
        }
    }
}
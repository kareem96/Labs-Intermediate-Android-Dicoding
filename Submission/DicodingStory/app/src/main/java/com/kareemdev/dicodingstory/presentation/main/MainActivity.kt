package com.kareemdev.dicodingstory.presentation.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.databinding.ActivityMainBinding
import com.kareemdev.dicodingstory.domain.viewmodel.SessionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sessionViewModel by viewModel<SessionViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        sessionViewModel.getRealtimeToken().observe(this){
            findNavController(R.id.fragmentContainerView).currentDestination?.let { nav ->
                if(nav.id == R.id.splashFragment2){
                    if(it.isEmpty()){
                        supportActionBar?.hide()
                        findNavController(R.id.fragmentContainerView).navigate(R.id.action_splashFragment2_to_loginFragment)
                    }else {
                        supportActionBar?.show()
                        findNavController(R.id.fragmentContainerView).navigate(R.id.action_splashFragment2_to_homeStoryFragment)
                    }
                }else{
                    if(it.isEmpty()){
                        supportActionBar?.hide()
                        findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeStoryFragment_to_loginFragment)
                    }else{
                        supportActionBar?.show()
                    }
                }
            }
        }
    }
}
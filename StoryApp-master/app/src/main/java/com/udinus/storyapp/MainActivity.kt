package com.udinus.storyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.udinus.storyapp.databinding.ActivityMainBinding
import com.udinus.storyapp.ui.home.HomeActivity
import com.udinus.storyapp.ui.home.HomeActivity.Companion.EXTRA_TOKEN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.viewModelScope.launch {
            mainViewModel.getAuth().collect{ tokens ->
                if(tokens.isNullOrEmpty()){
                    Intent(this@MainActivity, AuthActivity::class.java).also { intent ->
                        startActivity(intent)
                        finish()
                    }
                }else{
                    Intent(this@MainActivity, HomeActivity::class.java).also { intent ->
                        intent.putExtra(EXTRA_TOKEN
                            , tokens)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

    }
}
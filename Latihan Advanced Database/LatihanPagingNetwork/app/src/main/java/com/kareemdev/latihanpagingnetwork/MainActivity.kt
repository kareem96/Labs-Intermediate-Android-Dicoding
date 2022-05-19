package com.kareemdev.latihanpagingnetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareemdev.latihanpagingnetwork.adapter.QuoteListAdapter
import com.kareemdev.latihanpagingnetwork.databinding.ActivityMainBinding
import com.kareemdev.latihanpagingnetwork.ui.MainViewModel
import com.kareemdev.latihanpagingnetwork.ui.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvQuote.layoutManager = LinearLayoutManager(this)

        getData()

    }

    private fun getData() {
        val adapter = QuoteListAdapter()
        binding.rvQuote.adapter = adapter
        mainViewModel.getQuote()
        mainViewModel.quote.observe(this) {
            adapter.submitList(it)
        }
    }
}
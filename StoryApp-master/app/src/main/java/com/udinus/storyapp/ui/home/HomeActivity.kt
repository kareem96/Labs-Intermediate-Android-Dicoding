package com.udinus.storyapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udinus.storyapp.AuthActivity
import com.udinus.storyapp.R
import com.udinus.storyapp.databinding.ActivityHomeBinding
import com.udinus.storyapp.ui.adapter.LoadingAdapter
import com.udinus.storyapp.ui.adapter.StoryListAdapter
import com.udinus.storyapp.ui.create.CreateStoryActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@ExperimentalPagingApi
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var storyListAdapter: StoryListAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        const val EXTRA_TOKEN = "extra_token"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        stateLoading(true)

        storyListAdapter = StoryListAdapter()
        recyclerView = binding.rvStories
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = storyListAdapter.withLoadStateFooter(
            footer = LoadingAdapter{
                storyListAdapter.retry()
            }
        )

        recyclerView.setHasFixedSize(true)
        homeViewModel.getStory(intent.getStringExtra(EXTRA_TOKEN)!!).observe(this){
            storyListAdapter.submitData(lifecycle, it)
            stateLoading(false)
        }

        binding.btnCreateStory.setOnClickListener {
            Intent(this, CreateStoryActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_logout -> {
                toLogout()
                true
            }
            R.id.menu_setting ->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toLogout() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Are you sure?")
            .setMessage("You need to login again after logout")
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Logout") { _, _ ->
                homeViewModel.logout()
                Intent(this, AuthActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
                Toast.makeText(this, "Success logout", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun stateLoading(b: Boolean) {
        binding.progressBar.visibility = if (b) View.VISIBLE else View.GONE
    }
}
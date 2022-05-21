package com.farizdev.dicodingstoryapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farizdev.dicodingstoryapp.R
import com.farizdev.dicodingstoryapp.ViewModelFactory
import com.farizdev.dicodingstoryapp.adapter.StoryListAdapter
import com.farizdev.dicodingstoryapp.data.local.UserPreferences
import com.farizdev.dicodingstoryapp.databinding.ActivityMainBinding
import com.farizdev.dicodingstoryapp.ui.create.CreateActivity
import com.farizdev.dicodingstoryapp.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.title = resources.getString(R.string.app_name)

        binding.rvStories.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModelFactory()

        getUser()


        getDialog()

        viewModel.loading.observe(this) {
            stateLoading(it)
        }

        binding.fabAdd.setOnClickListener(this)
    }

    private fun getUser() {
        viewModel.getUser().observe(this){
            viewModel.getStories(it.token)
        }
    }

    private fun getDialog() {
        viewModel.errorMessage.observe(this) {
            if (it != "") {
                AlertDialog.Builder(this).apply {
                    setTitle(R.string.data_error)
                    setMessage(R.string.data_error_message.toString() + " $it")
                    setPositiveButton(R.string.ok) { _, _ -> }
                    create()
                    show()
                }
            }
        }

        viewModel.stories.observe(this) {
            if (it.size == 0) {
                AlertDialog.Builder(this).apply {
                    setTitle(R.string.info)
                    setMessage(R.string.empty_story)
                    setPositiveButton(R.string.ok) { _, _ -> }
                    create()
                    show()
                }
            }

            binding.rvStories.adapter = StoryListAdapter(it)
        }
    }

    private fun viewModelFactory() {
        viewModel = ViewModelProvider(this@MainActivity, ViewModelFactory.getInstance(
            UserPreferences.getInstance(dataStore)))[MainViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.menu_logout -> {
                AlertDialog.Builder(this).apply {
                    setTitle(R.string.logout)
                    setMessage(R.string.logout_confirmation)
                    setPositiveButton(R.string.yes) { _, _ ->
                        viewModel.logout()

                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                    setNegativeButton(R.string.cancel) { _, _ ->

                    }
                    create()
                    show()
                }
            }
        }
        return true
    }

    private fun stateLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                pbLoading.visibility = View.VISIBLE
                bgLoading.visibility = View.VISIBLE
            } else {
                pbLoading.visibility = View.INVISIBLE
                bgLoading.visibility = View.INVISIBLE
            }
        }
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.fab_add) startActivity(Intent(this, CreateActivity::class.java))
    }
}
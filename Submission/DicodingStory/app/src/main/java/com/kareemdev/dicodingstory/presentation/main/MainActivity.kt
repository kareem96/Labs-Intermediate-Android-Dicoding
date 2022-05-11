package com.kareemdev.dicodingstory.presentation.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.databinding.ActivityMainBinding
import com.kareemdev.dicodingstory.domain.viewmodel.SessionViewModel
import com.kareemdev.dicodingstory.utils.CommonFunction
import org.koin.androidx.viewmodel.ext.android.viewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sessionViewModel by viewModel<SessionViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                        if (nav.id != R.id.addStoryFragment){
                            supportActionBar?.show()
                            findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_homeStoryFragment)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.menu_logout).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CommonFunction.REQUEST_CODE_PERMISSIONS) {

            if (!CommonFunction.allPermissionsGranted(applicationContext)) {
                Toast.makeText(
                    findNavController(R.id.fragmentContainerView).context,
                    getString(R.string.failed_to_get_permissions),
                    Toast.LENGTH_LONG
                ).show()
                findNavController(R.id.fragmentContainerView).popBackStack()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_logout -> {
                findNavController(R.id.fragmentContainerView).currentDestination?.let {
                    when (it.id){
                        R.id.addStoryFragment -> {
                            item.isVisible = false
                            findNavController(R.id.fragmentContainerView).popBackStack()
                        }
                        else -> {}
                    }
                    sessionViewModel.removeToken()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
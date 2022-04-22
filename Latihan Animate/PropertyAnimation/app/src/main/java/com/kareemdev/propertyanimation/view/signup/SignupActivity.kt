package com.kareemdev.propertyanimation.view.signup

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.kareemdev.propertyanimation.R
import com.kareemdev.propertyanimation.ViewModelFactory
import com.kareemdev.propertyanimation.databinding.ActivitySignupBinding
import com.kareemdev.propertyanimation.model.UserModel
import com.kareemdev.propertyanimation.model.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when{
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Masukan name"
                }
                email.isEmpty() ->{
                    binding.emailEditTextLayout.error = "Masukan email"
                }
                password.isEmpty() ->{
                    binding.passwordEditTextLayout.error = "Masukan pasword"
                }
                else ->{
                    signupViewModel.saveUser(UserModel(name, email, password, false))
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah")
                        setMessage("Akunnya sudah jadi nih, Yuk, login dan belajar coding.")
                        setPositiveButton("lanjut"){ _, _ ->
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SignupViewModel::class.java]
    }

    private fun setupView() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }
        supportActionBar?.hide()
    }
}
package com.farizdev.dicodingstoryapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.farizdev.dicodingstoryapp.R
import com.farizdev.dicodingstoryapp.ViewModelFactory
import com.farizdev.dicodingstoryapp.data.local.UserPreferences
import com.farizdev.dicodingstoryapp.databinding.ActivityLoginBinding
import com.farizdev.dicodingstoryapp.ui.home.MainActivity
import com.farizdev.dicodingstoryapp.ui.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginVIewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()

        viewModelFactory()
        viewModel.emailValid.observe(this){
            loginValidation(it, viewModel.passwordValid.value!!)
        }
        viewModel.passwordValid.observe(this){
            loginValidation(viewModel.emailValid.value!!, it)
        }

        viewModel.loginStatus.observe(this){
            if(it){
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }

        viewModel.errorMsg.observe(this){
            var message = ""
            if(it == "Unauthorization"){
                message = "Email or password wrong"
            }else if(it != ""){
                message = "Email or password wrong $it"
            }
            if(message != ""){
                AlertDialog.Builder(this).apply {
                    setTitle(R.string.login_failed)
                    setMessage(message)
                    setPositiveButton(R.string.ok) { _, _ -> }
                    create()
                    show()
                }
            }
        }

        viewModel.loading.observe(this){
            stateLoading(it)
        }
        viewModel.getUser().observe(this){
            if(it.token != "" && !viewModel.loginStatus.value!!){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        with(binding){
            customEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                   emailValidation()
                }
            })

            customPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    passwordValidation()
                }
            })
        }

        binding.btnLogin.setOnClickListener(this)
        binding.toRegister.setOnClickListener(this)

    }

    private fun viewModelFactory() {
        viewModel = ViewModelProvider(this@LoginActivity, ViewModelFactory.getInstance(
            UserPreferences.getInstance(dataStore)))[LoginVIewModel::class.java]
    }

    private fun loginValidation(emailValidation: Boolean, passwordValidation: Boolean) {
        binding.btnLogin.isEnabled = emailValidation && passwordValidation
    }

    private fun passwordValidation() {
        viewModel.passwordStatus(binding.customPassword.valid)
    }
    private fun emailValidation() {
        viewModel.emailStatus(binding.customEmail.valid)
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
        when(view?.id){
            R.id.btn_login -> {
                viewModel.login(
                    binding.customEmail.text.toString(),
                    binding.customPassword.text.toString()
                )
            }
            R.id.to_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}
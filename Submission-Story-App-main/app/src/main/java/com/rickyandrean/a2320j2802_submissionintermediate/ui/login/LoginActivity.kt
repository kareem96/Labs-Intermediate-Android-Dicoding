package com.rickyandrean.a2320j2802_submissionintermediate.ui.login

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
import com.rickyandrean.a2320j2802_submissionintermediate.R
import com.rickyandrean.a2320j2802_submissionintermediate.databinding.ActivityLoginBinding
import com.rickyandrean.a2320j2802_submissionintermediate.helper.ViewModelFactory
import com.rickyandrean.a2320j2802_submissionintermediate.storage.UserPreference
import com.rickyandrean.a2320j2802_submissionintermediate.ui.main.MainActivity
import com.rickyandrean.a2320j2802_submissionintermediate.ui.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        loginViewModel = ViewModelProvider(
            this@LoginActivity,
            ViewModelFactory.getInstance(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.emailValid.observe(this) {
            loginValidation(it, loginViewModel.passwordValid.value!!)
        }

        loginViewModel.passwordValid.observe(this) {
            loginValidation(loginViewModel.emailValid.value!!, it)
        }

        loginViewModel.loginStatus.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }

        loginViewModel.errorMsg.observe(this) {
            var message = ""

            if (it == "Unauthorized") {
                message = resources.getString(R.string.wrong_authentication)
            } else if (it != "") {
                message = resources.getString(R.string.failed_login) + " $it"
            }

            if (message != "") {
                AlertDialog.Builder(this).apply {
                    setTitle(R.string.login_failed)
                    setMessage(message)
                    setPositiveButton(R.string.ok) { _, _ -> }
                    create()
                    show()
                }
            }
        }

        loginViewModel.loading.observe(this) {
            showLoading(it)
        }

        loginViewModel.getUser().observe(this) {
            if (it.token != "" && !loginViewModel.loginStatus.value!!) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        with(binding) {
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
                    setEmailValidation()
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
                    setPasswordValidation()
                }
            })
        }

        binding.btnLogin.setOnClickListener(this)
        binding.tvRegisterHyperlink.setOnClickListener(this)
    }

    private fun setEmailValidation() {
        loginViewModel.updateEmailStatus(binding.customEmail.valid)
    }

    private fun setPasswordValidation() {
        loginViewModel.updatePasswordStatus(binding.customPassword.valid)
    }

    private fun loginValidation(emailValidation: Boolean, passwordValidation: Boolean) {
        binding.btnLogin.isEnabled = emailValidation && passwordValidation
        binding.btnLogin.changeStatus(emailValidation && passwordValidation)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                loginViewModel.login(
                    binding.customEmail.text.toString(),
                    binding.customPassword.text.toString()
                )
            }
            R.id.tv_register_hyperlink -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}
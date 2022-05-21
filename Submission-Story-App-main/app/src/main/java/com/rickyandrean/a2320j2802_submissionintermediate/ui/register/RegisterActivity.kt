package com.rickyandrean.a2320j2802_submissionintermediate.ui.register

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.rickyandrean.a2320j2802_submissionintermediate.R
import com.rickyandrean.a2320j2802_submissionintermediate.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        registerViewModel.emailValid.observe(this) {
            loginValidation(it, registerViewModel.passwordValid.value!!)
        }

        registerViewModel.passwordValid.observe(this) {
            loginValidation(registerViewModel.emailValid.value!!, it)
        }

        registerViewModel.statusMessage.observe(this) {
            var message = ""
            var title = resources.getString(R.string.register_failed)

            when {
                it == "success" -> {
                    message = resources.getString(R.string.register_success_message)
                    title = resources.getString(R.string.register_success)
                }
                it == "Bad Request" -> {
                    message = resources.getString(R.string.invalid_authentication)
                }
                it != "" -> {
                    message = resources.getString(R.string.failed_register) + " $it"
                }
            }

            if (message != "") {
                AlertDialog.Builder(this).apply {
                    setTitle(title)
                    setMessage(message)
                    setPositiveButton(R.string.ok) { _, _ -> }
                    create()
                    show()
                }
            }
        }

        registerViewModel.loading.observe(this) {
            showLoading(it)
        }

        // Listener
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

        binding.btnRegister.setOnClickListener(this)
        binding.tvLoginHyperlink.setOnClickListener(this)
    }

    private fun setEmailValidation() {
        registerViewModel.updateEmailStatus(binding.customEmail.valid)
    }

    private fun setPasswordValidation() {
        registerViewModel.updatePasswordStatus(binding.customPassword.valid)
    }

    private fun loginValidation(emailValidation: Boolean, passwordValidation: Boolean) {
        binding.btnRegister.isEnabled = emailValidation && passwordValidation
        binding.btnRegister.changeStatus(emailValidation && passwordValidation)
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
            R.id.btn_register -> {
                registerViewModel.register(
                    binding.customName.text.toString(),
                    binding.customEmail.text.toString(),
                    binding.customPassword.text.toString()
                )
            }
            R.id.tv_login_hyperlink -> {
                finish()
            }
        }
    }
}
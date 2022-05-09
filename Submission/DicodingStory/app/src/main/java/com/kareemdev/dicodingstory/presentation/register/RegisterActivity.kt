package com.kareemdev.dicodingstory.presentation.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.data.model.RegisterResponse
import com.kareemdev.dicodingstory.data.remote.ApiConfig
import com.kareemdev.dicodingstory.databinding.ActivityMainBinding
import com.kareemdev.dicodingstory.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            val name = binding.myEtName.text.toString()
            val email = binding.myEtEmail.text.toString()
            val password = binding.myEtPassword.text.toString()

            when {
                name.isEmpty() -> {
                    binding.myEtName.error = "Name cannot be empty"
                    binding.myEtName.requestFocus()
                }
                email.isEmpty() -> {
                    binding.myEtEmail.error = "Email cannot be empty"
                    binding.myEtEmail.requestFocus()
                    showToast("")
                }
                password.isEmpty() -> {
                    binding.myEtPassword.error = "Password cannot be empty"
                    binding.myEtPassword.requestFocus()
                    showToast("")
                }
                else -> {
                    register()
                }
            }
        }
    }

    private fun register() {
        // method register
        onLoading(true)
        val client = ApiConfig.getApiService().register(
            binding.myEtName.text.toString(),
            binding.myEtEmail.text.toString(),
            binding.myEtPassword.text.toString()
        )
        client.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.error == true){
                        Toast.makeText(this@RegisterActivity, "", Toast.LENGTH_SHORT).show()
                    }else{
                        onLoading(false)
                        AlertDialog.Builder(this@RegisterActivity).apply {
                            setTitle("")
                            setMessage(responseBody.message)
                            setPositiveButton("Ok"){_,_, ->
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                }else{
                    onLoading(false)
                    Log.e(ContentValues.TAG, "onResponse: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "onFailure: ${t.message}", )
            }

        })

    }

    private fun onLoading(data: Boolean) {
        binding.progressBar.visibility = if(data) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(600)
        val name = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(600)
        val editName = ObjectAnimator.ofFloat(binding.myEtName, View.ALPHA, 1f).setDuration(600)
        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(600)
        val editEmail = ObjectAnimator.ofFloat(binding.myEtEmail, View.ALPHA, 1f).setDuration(600)
        val password = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(600)
        val editPassword = ObjectAnimator.ofFloat(binding.myEtPassword, View.ALPHA, 1f).setDuration(600)
        val register = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(600)

        AnimatorSet().apply {
            playSequentially(title, name, editName, email, editEmail, password, editPassword, register)
            startDelay = 600
            start()
        }
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
}
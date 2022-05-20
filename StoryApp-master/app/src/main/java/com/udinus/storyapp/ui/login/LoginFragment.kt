package com.udinus.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import com.google.android.material.snackbar.Snackbar
import com.udinus.storyapp.R
import com.udinus.storyapp.databinding.FragmentLoginBinding
import com.udinus.storyapp.ui.home.HomeActivity
import com.udinus.storyapp.ui.home.HomeActivity.Companion.EXTRA_TOKEN
import com.udinus.storyapp.utils.animateVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@ExperimentalPagingApi
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get()= _binding!!
    private var loginJob: Job = Job()
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toRegister.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
            )
            loginButton.setOnClickListener { handleLogin() }
        }
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title =
            ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val message =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.toRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
            )
            startDelay = 500
        }.start()
    }


    private fun handleLogin() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        stateLoading(true)
        lifecycleScope.launchWhenCreated {
            if(loginJob.isActive) loginJob.cancel()

            loginJob = launch {
                viewModel.login(email, password).collect{ results ->
                    results.onSuccess { credentials ->
                        credentials.loginResult?.token?.let { tokens ->
                            viewModel.saveToken(tokens)
                            Intent(requireContext(), HomeActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_TOKEN, tokens)
                                startActivity(intent)
                                requireActivity().finish()
                            }
                        }
                        Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                    }
                    results.onFailure {
                        Snackbar.make(
                            binding.root,
                            "Login Failed, try again",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        stateLoading(false)
                    }
                }
            }
        }
    }

    private fun stateLoading(b: Boolean) {
        binding.apply {
            emailEditText.isEnabled = !b
            passwordEditText.isEnabled = !b
            loginButton.isEnabled = !b

            if (b) {
                viewLoading.animateVisibility(true)

            } else {
                viewLoading.animateVisibility(false)
            }
        }
    }


}
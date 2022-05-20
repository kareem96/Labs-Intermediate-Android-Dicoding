package com.udinus.storyapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.google.android.material.snackbar.Snackbar
import com.udinus.storyapp.R
import com.udinus.storyapp.databinding.FragmentRegisterBinding
import com.udinus.storyapp.utils.animateVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
@ExperimentalPagingApi
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var registerJob: Job = Job()
    private val viewModelRegister: RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toLogin.setOnClickListener (
                Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment)
            )
            btnRegister.setOnClickListener {
                toRegister()
            }
        }
        playAnimation()
    }

    private fun toRegister() {
        val name = binding.nameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        stateLoading(true)

        lifecycleScope.launchWhenCreated {
            if(registerJob.isActive) registerJob.cancel()

            registerJob = launch {
                viewModelRegister.register(name, email, password).collect{results ->
                    results.onSuccess {
                        Toast.makeText(requireContext(), "Registration Success", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    results.onFailure {
                        Snackbar.make(
                            binding.root,
                            "Registration failed, try again!",
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
            nameEditText.isEnabled = !b
            emailEditText.isEnabled = !b
            passwordEditText.isEnabled = !b
            if (b) {
                viewLoading.animateVisibility(true)
            } else {
                viewLoading.animateVisibility(false)
            }
        }
    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title =
            ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val nameEditTextView = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.toLogin, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                nameEditTextView,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
            )
            startDelay = 500
        }.start()
    }



}
package com.kareemdev.dicodingstory.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.data.StateHandler
import com.kareemdev.dicodingstory.databinding.FragmentLoginBinding
import com.kareemdev.dicodingstory.domain.viewmodel.AuthViewModel
import com.kareemdev.dicodingstory.domain.viewmodel.SessionViewModel
import com.kareemdev.dicodingstory.utils.CommonFunction
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel by viewModel<AuthViewModel>()
    private val sessionViewModel by viewModel<SessionViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.tvToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener(this)
        authViewModel.loginState.observe(viewLifecycleOwner){
            when(it){
                is StateHandler.Loading -> {
                    showLoading()
                }
                is StateHandler.Error -> {
                    hideLoading()
                    CommonFunction.showSnackBar(
                        binding.root,
                        requireContext(),
                        "Login Gagal " +  it.message + "!",
                        true
                    )

                }
                is StateHandler.Success -> {
                    hideLoading()
                    CommonFunction.showSnackBar(
                        binding.root,
                        requireContext(),
                        getString(R.string.login_sucess),
                    )
                    it.data?.loginResult?.token?.let { token ->
                        sessionViewModel.saveToken(token)
                    }
                }
                else -> {}
            }
        }


        return binding.root
    }

    private fun hideLoading() {
        binding.progressbar.visibility = View.GONE
        binding.btnLogin.isEnabled = true
    }

    private fun showLoading() {
        binding.progressbar.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false
    }

    override fun onClick(p0: View?) {
        if(binding.edtEmail.error != null && binding.edtPassword.error != null) return
        when{
            binding.edtEmail.text?.isEmpty() == true -> {
                binding.edtEmail.error = "Email tidak valid"
                return
            }
            binding.edtPassword.text?.isEmpty() == true || (binding.edtPassword.text?.length ?: 0) < 6 -> {
                binding.edtPassword.error = "Password minimal 6 karatkter!"
                return
            }
        }
        authViewModel.login(
            binding.edtEmail.text.toString().trim(),
            binding.edtPassword.text.toString().trim(),
        )

    }


}
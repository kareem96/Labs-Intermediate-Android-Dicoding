package com.crm.siska.ui.auth.signin

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.crm.siska.FcmSharedPref
import com.crm.siska.Siska
import com.crm.siska.R
import com.crm.siska.model.response.device.DeviceResponse
import com.crm.siska.model.response.login.LoginResponse
import com.crm.siska.ui.MainActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_signin.*
import kotlin.math.log

class SigninFragment : Fragment(), SigninContract.View {

    lateinit var presenter: SigninPresenter
    var progressDialog : Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SigninPresenter(this)

        if (!Siska.getApp().getToken().isNullOrEmpty()) {
            val home = Intent(activity, MainActivity::class.java)
            startActivity(home)
            activity?.finish()
        }

//        initDummy()
        initView()

//        btnSignup.setOnClickListener {
//            val signup = Intent(activity, AuthActivity::class.java)
//            signup.putExtra("page_request", 2)
//            startActivity(signup)
//        }

        btnSignin.setOnClickListener {

            var email = et_username.text.toString()
            var password = et_password.text.toString()

            if (email.isNullOrEmpty()) {
                et_username.error = "Silahkan masukkan email Anda"
                et_username.requestFocus()
            } else if (password.isNullOrEmpty()) {
                et_password.error = "Silahkan masukkan password Anda"
                et_password.requestFocus()
            } else {
                presenter.subimtLogin(email, password)
            }

        }
    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {

        val androidID = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID);

        val pref = FcmSharedPref(requireContext())

        pref.getTokenFcm()
            ?.let { presenter.storetokenfcm(androidID, it, loginResponse.user.usernameKP) }

        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        Siska.getApp().setUser(json)
        Siska.getApp().setToken(loginResponse.access_token)

        val home = Intent(activity, MainActivity::class.java)
        startActivity(home)
        activity?.finish()
    }

    override fun onLoginFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFcmSuccess(deviceResponse: DeviceResponse) {
        TODO("Not yet implemented")
    }

    private fun initDummy() {
        et_username.setText("tes")
        et_password.setText("123456")
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}
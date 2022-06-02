package com.crm.siska.ui.setting

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.crm.siska.R
import com.crm.siska.Siska
import com.crm.siska.network.HttpClient
import com.crm.siska.ui.auth.AuthActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment(){

    var progressDialog : Dialog? = null
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnSubmit.setOnClickListener {
            var old_pass = etOldPass.text.toString()
            var new_pass = etNewPass.text.toString()
            var confirm_pass = etNewPassConfirm.text.toString()

            if (old_pass.isNullOrEmpty()) {
                etOldPass.error = "Masukkan Password lama"
                etOldPass.requestFocus()
            }else if (new_pass.isNullOrEmpty()) {
                etNewPass.error = "Masukkan Password baru"
                etNewPass.requestFocus()
            }else if (confirm_pass.isNullOrEmpty()){
                etNewPassConfirm.error = "Masukkan ulang password baru"
                etNewPassConfirm.requestFocus()
            }else if (etNewPass.text.toString() != etNewPassConfirm.text.toString()){
                etNewPassConfirm.error = "Konfirmasi Password tidak sesuai"
            }else{
                initView()
                changePassword(old_pass, new_pass, confirm_pass)
            }
        }
    }

    private fun changePassword(oldPass: String, newPass: String, confirmPass: String) {
        progressDialog?.show()
        val disposable = HttpClient.getInstance().getApi()!!.changePassword(oldPass, newPass, confirmPass)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    progressDialog?.dismiss()

                    if (it.meta?.status.equals("success",true)){
                        Siska.getApp().setUser("")
                        Siska.getApp().setToken("")
                        val success = Intent(activity, SuccessActivity::class.java)
                        startActivity(success)
                        activity?.finish()
                    } else {
                        Toast.makeText(activity, it.meta?.message, Toast.LENGTH_LONG).show()
                    }
                },
                {
                    progressDialog?.dismiss()
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG).show()
                }
            )

        mCompositeDisposable!!.add(disposable)
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

}
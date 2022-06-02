package com.crm.siska.ui.account

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crm.siska.R
import com.crm.siska.Siska
import com.crm.siska.ui.auth.AuthActivity
import com.crm.siska.ui.detail.DetailActivity
import com.crm.siska.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.dialog_logout.view.*
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {


    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvLogout.setOnClickListener {
            val view = View.inflate(activity, R.layout.dialog_logout, null)

            val builder = AlertDialog.Builder(activity)
            builder.setView(view)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            view.btnYes.setOnClickListener {
                initView()
                Siska.getApp().setUser("")
                Siska.getApp().setToken("")
                if (Siska.getApp().getToken().isNullOrEmpty()) {
                    val login = Intent(activity, AuthActivity::class.java)
                    startActivity(login)
                    activity?.finish()
                }
            }

            view.btnNo.setOnClickListener {
                dialog.dismiss()
            }


        }

        tvChangePass.setOnClickListener {
            val setting = Intent(activity, SettingActivity::class.java)
            startActivity(setting)
        }
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
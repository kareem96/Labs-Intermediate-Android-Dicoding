package com.crm.siska.ui.profile

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crm.siska.R
import com.crm.siska.Siska
import com.crm.siska.model.response.login.User
import com.crm.siska.ui.home.HomePresenter
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable


class ProfilePictureFragment : Fragment(){

    var bundle:Bundle ?= null
    var progressDialog : Dialog? = null
    private lateinit var presenter: HomePresenter

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_picture, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val user = Siska.getApp().getUser()
//        Toast.makeText(activity, Siska.getApp().getUser(), Toast.LENGTH_LONG).show()
            initView()

    }

    private fun initView() {

//        Glide.with(requireActivity())
//            .load("https://api-siska.makutapro.id/storage/photo/"+userResponse.photoUser)
//            .apply(RequestOptions.circleCropTransform())
//            .into(img_profile)
    }

}


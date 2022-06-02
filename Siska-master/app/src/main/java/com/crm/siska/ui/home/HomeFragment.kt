package com.crm.siska.ui.home

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crm.siska.R
import com.crm.siska.model.response.home.HomeResponse
import com.crm.siska.network.HttpClient
import com.crm.siska.ui.home.hotprospect.HotProspectActivity
import com.crm.siska.ui.home.myprospect.MyProspectActivity
import com.crm.siska.ui.home.newprospect.NewProspectActivity
import com.crm.siska.utils.GlideHelper
import com.crm.siska.utils.Permission
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class HomeFragment : Fragment(), HomeAdapter.ItemAdapterCallback, HomeContract.View {

    private lateinit var presenter: HomePresenter
    var progressDialog: Dialog? = null
    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    private lateinit var permission: Permission
    var fileImage: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        permission = Permission()
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        presenter = HomePresenter(this)
        presenter.getHome()
        btnMyProspect.setOnClickListener {
            val myprospect = Intent(activity, MyProspectActivity::class.java)
            startActivity(myprospect)
        }
        btnHotProspect.setOnClickListener {
            val hotprospect = Intent(activity, HotProspectActivity::class.java)
            startActivity(hotprospect)
        }
        btnNewProspect.setOnClickListener {
            val hotprospect = Intent(activity, NewProspectActivity::class.java)
            startActivity(hotprospect)
        }
        btnBooking.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://booking.makutapro.id/login"))
            startActivity(browserIntent)
        }
        btnSetting.setOnClickListener {
            EasyImage.openGallery(this, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onImagePicked(
                    imageFile: File?,
                    source: EasyImage.ImageSource?,
                    type: Int
                ) {
                    fileImage = imageFile
                    GlideHelper.setImageFile(requireActivity(), imageFile, img_profile)
                    if (fileImage != null) {
                        uploadImage(fileImage)
                    }
                }
            })
    }
    private fun convertFile(file: File?): MultipartBody.Part {
        val requestBody: RequestBody = file!!.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestBody)
    }
    private fun uploadImage(file: File?) {
        val disposable = HttpClient.getInstance().getApi()!!.changeProfileImg(convertFile(file))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    progressDialog?.dismiss()

                    if (it.meta?.status.equals("success", true)) {
                        Toast.makeText(activity, it.data.toString(), Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(activity, it.meta?.message, Toast.LENGTH_LONG).show()
                    }
                },
                {
                    /*progressDialog?.dismiss()*/
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

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        tv_namasales.text = homeResponse.namaSales
        tvNamaProject.text = homeResponse.namaProject
        textViewProspect.text = homeResponse.prospect.toString()
        textViewProcess.text = homeResponse.process.toString()
        textViewHot.text = homeResponse.hot.toString()
        textViewClosing.text = homeResponse.closing.toString()
        if (!homeResponse.photoUser.isNullOrEmpty()) {
            Glide.with(requireActivity())
                .load("https://api-siska.makutapro.id/storage/photo/" + homeResponse.photoUser)
                .apply(RequestOptions.circleCropTransform())
                .into(img_profile)
            img_profile.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.bg_pp
                )
            )
        }
    }

    override fun onHomeFailed(message: String) {
//        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(v: View, data: HomeResponse) {

    }



}


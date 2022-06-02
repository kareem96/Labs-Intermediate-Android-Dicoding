package com.crm.siska.ui.input

import android.util.Log
import android.view.View
import com.crm.siska.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class  InputPresenter (private val view: InputContract.View) : InputContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getData() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.data()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onDataSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onDataFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onDataFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun inputData(
        namaProspect: String,
        emailProspect: String,
        hp: String,
        message: String,
        genderId: Int,
        usiaId: Int,
        pekerjaanId: Int,
        penghasilanId: Int,
        unitId: Int,
        tempatTinggalId: Int,
        tempatKerjaId: Int,
        viewParms: View
    ) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.prospect(
            namaProspect, emailProspect, hp, message, genderId, usiaId, pekerjaanId, penghasilanId, unitId, tempatTinggalId, tempatKerjaId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        Log.v("venom","sukses")
                        it.data?.let { it1 -> view.onProspectSuccess(it1, viewParms) }
                    } else if (it.meta?.status.equals("error",true)) {
                        it.data?.let { it1 -> view.onProspectFailed(it1.toString()) }
//                        it.meta?.message?.let { it1 -> view.onProspectFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onProspectFailed("Nomor Handphone sudah terdaftar")
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }

}
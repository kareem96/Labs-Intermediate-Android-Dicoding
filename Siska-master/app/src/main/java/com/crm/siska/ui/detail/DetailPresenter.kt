package com.crm.siska.ui.detail

import android.view.View
import com.crm.siska.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class  DetailPresenter (private val view: DetailContract.View) : DetailContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getDetail(id:Int) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.detail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onDetailSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onDetailFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onDetailFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun updateprospect(
        prospectID: Int,
        genderId: Int,
        usiaId: Int,
        pekerjaanId: Int,
        penghasilanId: Int,
        unitId: Int,
        tempatTinggalId: Int,
        tempatKerjaId: Int,
        catatanSales: String,
        viewParms: View
    ) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.prospectupdate(
            prospectID,
            genderId,
            usiaId,
            pekerjaanId,
            penghasilanId,
            unitId,
            tempatTinggalId,
            tempatKerjaId,
            catatanSales
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onUpdateSuccess(it1,viewParms) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onUpdateFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onUpdateFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun fuwa(prospectID: Int) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.fuwa(prospectID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){

                    } else {
                        it.meta?.message?.let { it1 -> view.onUpdateFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onUpdateFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun futelp(prospectID: Int) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.futelp(prospectID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){

                    } else {
                        it.meta?.message?.let { it1 -> view.onUpdateFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onUpdateFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun hotleads(prospectID: Int) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.hotleads(prospectID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){

                    } else {
                        it.meta?.message?.let { it1 -> view.onUpdateFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onUpdateFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun closing(prospectID: Int, unitID: Int, ketUnit: String, hargaJual: Int) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.closing(prospectID, unitID, ketUnit, hargaJual)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){

                    } else {
                        it.meta?.message?.let { it1 -> view.onUpdateFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onUpdateFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun notInterested(prospectID: Int, notInterestedID: Int) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.notinterested(prospectID, notInterestedID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){

                    } else {
                        it.meta?.message?.let { it1 -> view.onUpdateFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onUpdateFailed(it.message.toString())
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
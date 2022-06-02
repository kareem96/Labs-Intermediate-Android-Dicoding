package com.crm.siska.ui.auth.signin

import android.util.Log
import com.crm.siska.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SigninPresenter (private val view:SigninContract.View) : SigninContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun subimtLogin(username: String, password: String) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onLoginSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onLoginFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onLoginFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun storetokenfcm(deviceId: String, tokenFcm: String, usernameKp: String) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.storetokenfcm(deviceId, tokenFcm, usernameKp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onFcmSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onLoginFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onLoginFailed(it.message.toString())
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
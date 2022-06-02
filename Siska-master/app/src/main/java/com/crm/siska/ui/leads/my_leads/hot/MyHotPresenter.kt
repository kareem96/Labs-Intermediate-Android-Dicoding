package com.crm.siska.ui.leads.my_leads.hot

import com.crm.siska.network.HttpClient
import com.crm.siska.ui.leads.LeadsContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class  MyHotPresenter (private val view: LeadsContract.View) : LeadsContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getLeads() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.myhot()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onLeadsSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onLeadsFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onLeadsFailed(it.message.toString())
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
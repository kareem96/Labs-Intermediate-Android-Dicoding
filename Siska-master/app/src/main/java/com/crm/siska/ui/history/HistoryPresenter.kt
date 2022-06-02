package com.crm.siska.ui.history

import com.crm.siska.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class  HistoryPresenter (private val view: HistoryContract.View) : HistoryContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }
    override fun getHistory() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.history()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onHistorySuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onHistoryFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onHistoryFailed(it.message.toString())
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
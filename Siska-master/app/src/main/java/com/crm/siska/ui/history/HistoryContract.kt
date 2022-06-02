package com.crm.siska.ui.history

import com.crm.siska.base.BasePresenter
import com.crm.siska.base.BaseView
import com.crm.siska.model.response.history.HistoryResponse

interface HistoryContract {

    interface View: BaseView {
        fun onHistorySuccess(historyResponse: HistoryResponse)
        fun onHistoryFailed(message:String)
    }

    interface Presenter : HistoryContract, BasePresenter {
        fun getHistory()
    }

}
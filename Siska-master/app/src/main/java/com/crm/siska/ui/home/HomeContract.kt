package com.crm.siska.ui.home

import com.crm.siska.base.BasePresenter
import com.crm.siska.base.BaseView
import com.crm.siska.model.response.home.HomeResponse

interface HomeContract {

    interface View: BaseView {
        fun onHomeSuccess(homeResponse: HomeResponse)
        fun onHomeFailed(message:String)
    }

    interface Presenter : HomeContract, BasePresenter {
        fun getHome()
    }

}
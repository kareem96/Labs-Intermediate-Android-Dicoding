package com.crm.siska.ui.leads

import com.crm.siska.base.BasePresenter
import com.crm.siska.base.BaseView
import com.crm.siska.model.response.leads.LeadsResponse

interface LeadsContract {

    interface View: BaseView {
        fun onLeadsSuccess(leadsResponse: LeadsResponse)
        fun onLeadsFailed(message:String)
    }

    interface Presenter : LeadsContract, BasePresenter {
        fun getLeads()
    }
}
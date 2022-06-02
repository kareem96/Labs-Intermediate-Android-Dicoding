package com.crm.siska.ui.input

import com.crm.siska.base.BasePresenter
import com.crm.siska.base.BaseView
import com.crm.siska.model.response.input.InputResponse
import com.crm.siska.model.response.input.ProspectResponse

interface InputContract {

    interface View: BaseView {
        fun onDataSuccess(inputResponse: InputResponse)
        fun onDataFailed(message:String)
        fun onProspectSuccess(prospectResponse: ProspectResponse, view: android.view.View)
        fun onProspectFailed(message: String)
    }

    interface Presenter : InputContract, BasePresenter {
        fun getData()
        fun inputData(namaProspect:String, emailProspect:String, hp:String, message:String, genderId:Int, usiaId:Int, pekerjaanId:Int, penghasilanId:Int, unitId:Int, tempatTinggalId:Int, tempatKerjaId:Int, view: android.view.View )
    }

}
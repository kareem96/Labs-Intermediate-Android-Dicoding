package com.crm.siska.ui.detail

import com.crm.siska.base.BasePresenter
import com.crm.siska.base.BaseView
import com.crm.siska.model.response.detail.DetailResponse
import com.crm.siska.model.response.input.ProspectResponse

interface DetailContract {

    interface View: BaseView {
        fun onDetailSuccess(detailResponse: DetailResponse)
        fun onDetailFailed(message:String)
        fun onUpdateSuccess(prospectResponse: ProspectResponse, view: android.view.View)
        fun onUpdateFailed(message: String)
    }

    interface Presenter : DetailContract, BasePresenter {
        fun getDetail(id:Int)
        fun updateprospect(prospectID:Int, genderId:Int, usiaId:Int, pekerjaanId:Int, penghasilanId:Int, unitId:Int, tempatTinggalId:Int, tempatKerjaId:Int, catatanSales:String, view: android.view.View )
        fun fuwa(prospectID: Int)
        fun futelp(prospectID: Int)
        fun hotleads(prospectID: Int)
        fun closing(prospectID: Int, unitID: Int, ketUnit: String, hargaJual: Int)
        fun notInterested(prospectID: Int, notInterestedID: Int)
    }

}
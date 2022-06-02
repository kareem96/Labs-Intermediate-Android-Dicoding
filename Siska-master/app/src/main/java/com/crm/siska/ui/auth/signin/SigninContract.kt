package com.crm.siska.ui.auth.signin

import com.crm.siska.base.BasePresenter
import com.crm.siska.base.BaseView
import com.crm.siska.model.response.device.DeviceResponse
import com.crm.siska.model.response.login.LoginResponse

interface SigninContract {

    interface View: BaseView {
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onLoginFailed(message:String)
        fun onFcmSuccess(deviceResponse: DeviceResponse)
    }

    interface Presenter : SigninContract, BasePresenter {
        fun subimtLogin(email:String, password:String)
        fun storetokenfcm(deviceId : String, tokenFcm : String, usernameKp : String)
    }
}
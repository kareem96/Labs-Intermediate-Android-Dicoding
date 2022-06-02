package com.crm.siska.model.response.device


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeviceResponse(
    @Expose
    @SerializedName("DeviceID")
    val deviceId : String,
    @Expose
    @SerializedName("TokenFCM")
    val tokenFcm : String,
    @Expose
    @SerializedName("UsernameKP")
    val usernameKp : String
)
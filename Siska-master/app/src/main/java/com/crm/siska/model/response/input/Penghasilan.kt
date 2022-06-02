package com.crm.siska.model.response.input


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Penghasilan(
    @Expose
    @SerializedName("PenghasilanID")
    val penghasilanID: Int,
    @Expose
    @SerializedName("RangePenghasilan")
    val rangePenghasilan: String
)
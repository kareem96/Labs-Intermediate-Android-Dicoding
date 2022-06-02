package com.crm.siska.model.response.detail


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pekerjaan(
    @Expose
    @SerializedName("PekerjaanID")
    val pekerjaanID: Int,
    @Expose
    @SerializedName("TipePekerjaan")
    val tipePekerjaan: String
)
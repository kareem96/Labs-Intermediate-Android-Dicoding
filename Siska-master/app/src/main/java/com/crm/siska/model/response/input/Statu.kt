package com.crm.siska.model.response.input


import com.google.gson.annotations.SerializedName

data class Statu(
    @SerializedName("KetStatus")
    val ketStatus: String,
    @SerializedName("StatusID")
    val statusID: Int
)
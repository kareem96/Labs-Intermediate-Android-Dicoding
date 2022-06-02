package com.crm.siska.model.response.detail


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Provinsi(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("province")
    val province: String
)
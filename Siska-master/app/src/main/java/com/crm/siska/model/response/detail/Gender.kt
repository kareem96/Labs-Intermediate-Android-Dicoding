package com.crm.siska.model.response.detail


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Gender(
    @Expose
    @SerializedName("GenderID")
    val genderID: Int,
    @Expose
    @SerializedName("JenisKelamin")
    val jenisKelamin: String
)
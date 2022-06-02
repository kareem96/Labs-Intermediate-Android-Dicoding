package com.crm.siska.model.response.input


import com.google.gson.annotations.SerializedName

data class Gender(
    @SerializedName("GenderID")
    val genderID: Int,
    @SerializedName("JenisKelamin")
    val jenisKelamin: String
)
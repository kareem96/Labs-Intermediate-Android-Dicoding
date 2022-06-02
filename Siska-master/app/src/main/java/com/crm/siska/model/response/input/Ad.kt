package com.crm.siska.model.response.input


import com.google.gson.annotations.SerializedName

data class Ad(
    @SerializedName("JenisAds")
    val jenisAds: String,
    @SerializedName("KodeAds")
    val kodeAds: String
)
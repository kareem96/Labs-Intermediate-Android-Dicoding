package com.crm.siska.model.response.input


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("NamaSumber")
    val namaSumber: String,
    @SerializedName("SumberDataID")
    val sumberDataID: Int
)
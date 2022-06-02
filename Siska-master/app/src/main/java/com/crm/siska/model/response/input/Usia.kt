package com.crm.siska.model.response.input


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Usia(
    @Expose
    @SerializedName("RangeUsia")
    val rangeUsia: String,
    @Expose
    @SerializedName("UsiaID")
    val usiaID: Int
)
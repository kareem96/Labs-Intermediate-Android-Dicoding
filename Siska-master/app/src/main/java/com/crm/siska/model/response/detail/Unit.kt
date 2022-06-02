package com.crm.siska.model.response.detail


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Unit(
    @Expose
    @SerializedName("UnitID")
    val unitID: Int,
    @Expose
    @SerializedName("UnitName")
    val unitName: String
)
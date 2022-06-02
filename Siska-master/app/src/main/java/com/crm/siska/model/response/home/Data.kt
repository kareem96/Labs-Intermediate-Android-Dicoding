package com.crm.siska.model.response.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data (
    @Expose
    @SerializedName("Prospect")
    val prospect: Int,
    @Expose
    @SerializedName("Closing")
    val closing: Int,
    @Expose
    @SerializedName("Hot")
    val hot: Int,
    @Expose
    @SerializedName("Process")
    val process: Int,
        )
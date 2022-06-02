package com.crm.siska.model.response.detail


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NotInterested(
    @Expose
    @SerializedName("Alasan")
    val alasan: String,
    @Expose
    @SerializedName("NotInterestedID")
    val notInterestedID: Int
)
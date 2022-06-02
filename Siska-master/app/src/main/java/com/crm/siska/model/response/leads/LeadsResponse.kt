package com.crm.siska.model.response.leads


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LeadsResponse(
    @Expose
    @SerializedName("data")
    val data: List<Data>
)
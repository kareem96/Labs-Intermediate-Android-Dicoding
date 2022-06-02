package com.crm.siska.model.response.input


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProspectResponse(
    @Expose
    @SerializedName("data")
    val data: String,
)
package com.crm.siska.model.response.input


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Kota(
    @Expose
    @SerializedName("city")
    val city: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("postal_code")
    val postalCode: String,
    @Expose
    @SerializedName("province_id")
    val provinceId: Int,
    @Expose
    @SerializedName("type")
    val type: String
)
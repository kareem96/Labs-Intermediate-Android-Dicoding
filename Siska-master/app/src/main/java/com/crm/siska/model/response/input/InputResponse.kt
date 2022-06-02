package com.crm.siska.model.response.input


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InputResponse(
    @Expose
    @SerializedName("ads")
    val ads: List<Ad>,
    @Expose
    @SerializedName("gender")
    val gender: List<Gender>,
    @Expose
    @SerializedName("kota")
    val kota: List<Kota>,
    @Expose
    @SerializedName("pekerjaan")
    val pekerjaan: List<Pekerjaan>,
    @Expose
    @SerializedName("penghasilan")
    val penghasilan: List<Penghasilan>,
    @Expose
    @SerializedName("provinsi")
    val provinsi: List<Provinsi>,
    @Expose
    @SerializedName("source")
    val source: List<Source>,
    @Expose
    @SerializedName("status")
    val status: List<Statu>,
    @Expose
    @SerializedName("unit")
    val unit: List<Unit>,
    @Expose
    @SerializedName("usia")
    val usia: List<Usia>
)
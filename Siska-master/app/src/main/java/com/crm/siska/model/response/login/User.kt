package com.crm.siska.model.response.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("Email")
    val email: String,
    @Expose
    @SerializedName("Hp")
    val hp: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("JoinDate")
    val joinDate: String,
    @Expose
    @SerializedName("KodeSales")
    val kodeSales: String,
    @Expose
    @SerializedName("NamaAgent")
    val namaAgent: String,
    @Expose
    @SerializedName("NamaProject")
    val namaProject: String,
    @Expose
    @SerializedName("NamaSales")
    val namaSales: String,
    @Expose
    @SerializedName("PhotoUser")
    val photoUser: String,
    @Expose
    @SerializedName("UsernameKP")
    val usernameKP: String
)
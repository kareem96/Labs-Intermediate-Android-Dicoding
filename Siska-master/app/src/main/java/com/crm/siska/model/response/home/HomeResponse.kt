package com.crm.siska.model.response.home


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @Expose
    @SerializedName("Email")
    val email: String,
    @Expose
    @SerializedName("Hp")
    val hp: String,
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
    val usernameKP: String,
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
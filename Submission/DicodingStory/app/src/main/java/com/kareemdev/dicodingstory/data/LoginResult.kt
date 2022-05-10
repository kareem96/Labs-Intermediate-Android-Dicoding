package com.kareemdev.dicodingstory.data

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @field:SerializedName("email")
    val email:String,
    @field:SerializedName("password")
    val password:String,
)
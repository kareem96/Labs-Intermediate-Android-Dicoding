package com.kareemdev.dicodingstory.data.model

import com.google.gson.annotations.SerializedName
import com.kareemdev.dicodingstory.domain.model.ListStoryItem

data class BaseResponse(
    @field:SerializedName("listStory")
    val listStory: List<ListStoryItem>,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("loginResult")
    val loginResult: LoginResult? = null,
)

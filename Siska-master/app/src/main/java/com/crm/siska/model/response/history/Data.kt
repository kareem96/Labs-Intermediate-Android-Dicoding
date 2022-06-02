package com.crm.siska.model.response.history


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data(
    @Expose
    @SerializedName("created_at")
    val createdAt: String,
    @Expose
    @SerializedName("day")
    val day: Int,
    @Expose
    @SerializedName("HistoryBy")
    val historyBy: String,
    @Expose
    @SerializedName("hour")
    val hour: Int,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("KodeProject")
    val kodeProject: String,
    @Expose
    @SerializedName("KodeSales")
    val kodeSales: String,
    @Expose
    @SerializedName("minute")
    val minute: Int,
    @Expose
    @SerializedName("month")
    val month: String,
    @Expose
    @SerializedName("Notes")
    val notes: String,
    @Expose
    @SerializedName("NotesDev")
    val notesDev: Any,
    @Expose
    @SerializedName("Subject")
    val subject: String,
    @Expose
    @SerializedName("SubjectDev")
    val subjectDev: Any,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: String
)
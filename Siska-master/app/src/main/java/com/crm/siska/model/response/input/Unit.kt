package com.crm.siska.model.response.input


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Unit(
    @Expose
    @SerializedName("Active")
    val active: Int,
    @Expose
    @SerializedName("DateCreated")
    val dateCreated: Any,
    @Expose
    @SerializedName("DateUpdated")
    val dateUpdated: Any,
    @Expose
    @SerializedName("LevelInputID")
    val levelInputID: String,
    @Expose
    @SerializedName("ProjectCode")
    val projectCode: String,
    @Expose
    @SerializedName("SubProject")
    val subProject: Any,
    @Expose
    @SerializedName("SubProjectCode")
    val subProjectCode: Any,
    @Expose
    @SerializedName("UnitClass")
    val unitClass: Any,
    @Expose
    @SerializedName("UnitID")
    val unitID: Int,
    @Expose
    @SerializedName("UnitName")
    val unitName: String,
    @Expose
    @SerializedName("UnitSpec")
    val unitSpec: Any
)
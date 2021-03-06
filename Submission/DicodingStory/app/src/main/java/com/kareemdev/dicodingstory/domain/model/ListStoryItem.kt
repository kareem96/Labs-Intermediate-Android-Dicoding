package com.kareemdev.dicodingstory.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "stories")
@Parcelize
data class ListStoryItem(

    @ColumnInfo(name = "photoUrl")
    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @ColumnInfo(name = "createdAt")
    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    @field:SerializedName("description")
    val description: String? = null,

    @ColumnInfo(name = "lon")
    @field:SerializedName("lon")
    val lon: Double? = null,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @ColumnInfo(name = "lat")
    @field:SerializedName("lat")
    val lat: Double? = null

): Parcelable
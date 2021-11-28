package com.gsm.data.entity.mission.response


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.gsm.data.util.Constants.Companion.TABLE_NAME

    @Entity(tableName = TABLE_NAME)
data class GetMissionResponse(
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("exp")
    val exp: Int?,
    @SerializedName("expiredAt")
    val expiredAt: Int?,
    @SerializedName("id")
    @PrimaryKey
    val id: Int?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)
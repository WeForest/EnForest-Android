package com.gsm.data.entity.mission.response


import com.google.gson.annotations.SerializedName

data class GetMissionTypeResponse(
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("exp")
    val exp: Int?,
    @SerializedName("expiredAt")
    val expiredAt: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("level")
    val level: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)
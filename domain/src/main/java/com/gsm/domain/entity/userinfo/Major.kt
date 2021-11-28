package com.gsm.domain.entity.userinfo


import com.google.gson.annotations.SerializedName

data class Major(
    @SerializedName("id")
    val id: Int,
    @SerializedName("major")
    val major: String,
    @SerializedName("userId")
    val userId: Int
)
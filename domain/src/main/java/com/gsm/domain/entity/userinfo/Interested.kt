package com.gsm.domain.entity.userinfo


import com.google.gson.annotations.SerializedName

data class Interested(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Interested")
    val interested: String,
    @SerializedName("userId")
    val userId: Int
)
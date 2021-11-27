package com.gsm.domain.entity.userinfo

import com.google.gson.annotations.SerializedName

data class GetUSerInfoEntityP(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<Result>
)
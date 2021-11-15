package com.gsm.data.entity.sign.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("token")
    val token: String?
)
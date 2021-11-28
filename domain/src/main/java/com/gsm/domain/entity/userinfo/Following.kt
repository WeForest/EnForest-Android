package com.gsm.domain.entity.userinfo


import com.google.gson.annotations.SerializedName

data class Following(
    @SerializedName("accessToken")
    val accessToken: Any,
    @SerializedName("authCompany")
    val authCompany: Any,
    @SerializedName("companyEmail")
    val companyEmail: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("exp")
    val exp: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isJobSeeker")
    val isJobSeeker: Boolean,
    @SerializedName("level")
    val level: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImg")
    val profileImg: Any,
    @SerializedName("purpose")
    val purpose: String,
    @SerializedName("sub")
    val sub: String
)
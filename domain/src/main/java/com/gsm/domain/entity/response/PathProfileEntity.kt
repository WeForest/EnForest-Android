package com.gsm.domain.entity.response

import com.google.gson.annotations.SerializedName

data class PathProfileEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("sub")
    val sub: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImg")
    val profileImg: Boolean,
    @SerializedName("isJobSeeker")
    val isJobSeeker: String,
    @SerializedName("companyEmail")
    val companyEmail: Boolean,
    @SerializedName("authCompany")
    val authCompany: String,
    @SerializedName("purpose")
    val purpose: Int,
    @SerializedName("level")
    val level: Int,
    @SerializedName("exp")
    val exp: String
)
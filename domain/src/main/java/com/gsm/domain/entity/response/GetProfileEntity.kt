package com.gsm.domain.entity.response

import com.google.gson.annotations.SerializedName

data class GetProfileEntity(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("sub")
    val sub: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profileImg")
    val profileImg: String?,
    @SerializedName("isJobSeeker")
    val isJobSeeker: Boolean?,
    @SerializedName("companyEmail")
    val companyEmail: String?,
    @SerializedName("authCompany")
    val authCompany: Boolean?,
    @SerializedName("purpose")
    val purpose: String?,
    @SerializedName("level")
    val level: Int?,
    @SerializedName("exp")
    val exp: Int?
)
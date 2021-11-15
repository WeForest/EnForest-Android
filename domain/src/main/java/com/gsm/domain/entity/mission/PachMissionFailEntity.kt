package com.gsm.domain.entity.mission


import com.google.gson.annotations.SerializedName

data class PachMissionFailEntity(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("authCompany")
    val authCompany: Boolean?,
    @SerializedName("companyEmail")
    val companyEmail: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("exp")
    val exp: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isJobSeeker")
    val isJobSeeker: Boolean?,
    @SerializedName("level")
    val level: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profileImg")
    val profileImg: String?,
    @SerializedName("purpose")
    val purpose: String?,
    @SerializedName("sub")
    val sub: String?
)
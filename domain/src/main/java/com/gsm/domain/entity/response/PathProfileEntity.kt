package com.gsm.domain.entity.response

import com.google.gson.annotations.SerializedName

data class PathProfileEntity(
    val id: String?,
    val sub: String?,
    val name: String?,
    val profileImg: String?,
    val isJobSeeker: Boolean?,
    val companyEmail: String?,
    val authCompany: Boolean?,
    val purpose: String?,
    val level: Int?,
    val exp: Int?
)
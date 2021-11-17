package com.gsm.domain.entity.response

import com.google.gson.annotations.SerializedName

data class PathProfileEntity(
    val id: String?,
    val sub: String?,
    val name: String?,
    val profileImg: Boolean?,
    val isJobSeeker: String?,
    val companyEmail: Boolean?,
    val authCompany: String?,
    val purpose: Int?,
    val level: Int?,
    val exp: String?
)
package com.gsm.domain.entity.mission


data class PathMissionClearEntity(
    val accessToken: String?,
    val authCompany: Boolean?,
    val companyEmail: String?,
    val email: String?,
    val exp: Int?,
    val id: Int?,
    val isJobSeeker: Boolean?,
    val level: Int?,
    val name: String?,
    val profileImg: String?,
    val purpose: String?,
    val sub: String?
)
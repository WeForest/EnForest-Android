package com.gsm.presentation.data.dto



data class User(
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
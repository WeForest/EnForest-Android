package com.gsm.domain.entity.response

data class LoginEntity(
    val message: String?,
    val success: Boolean?,
    val token: String?
)
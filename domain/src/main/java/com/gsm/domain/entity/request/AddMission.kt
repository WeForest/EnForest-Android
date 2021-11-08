package com.gsm.domain.entity.request

data class AddMission(
    val level: Int?,
    val exp: Int?,
    val title: String?,
    val content: String?,
    val expiredAt: Int?,
    val type:String?
)
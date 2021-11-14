package com.gsm.domain.entity.mission.request

data class AddMission(
    val level: String?,
    val exp: Int?,
    val title: String?,
    val content: String?,
    val expiredAt: Int?,
    val type:String?
)
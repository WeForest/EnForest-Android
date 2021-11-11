package com.gsm.data.entity.mission.request

data class AddMissionRequest(
    val level: String?,
    val exp: Int?,
    val title: String?,
    val content: String?,
    val expiredAt: Int?,
    val type:String?
)
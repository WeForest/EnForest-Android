package com.gsm.domain.entity.mission



data class DeleteMissionEntity(
    val content: String?,
    val createdAt: String?,
    val exp: Int?,
    val expiredAt: Int?,
    val id: Int?,
    val level: Int?,
    val title: String?,
    val type: String?
)
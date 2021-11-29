package com.gsm.domain.entity.mission

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AddMissionEntity(
    val content: String?,
    val createdAt: String?,
    val exp: Int?,
    val expiredAt: Int?,
    val id: Int?,
    val level: Int?,
    val title: String?,
    val type: String?
) : Parcelable
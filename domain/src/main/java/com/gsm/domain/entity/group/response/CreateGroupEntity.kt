package com.gsm.domain.entity.group.response

import com.google.gson.annotations.SerializedName

data class CreateGroupEntity(
    val description: String?,
    val id: Int?,
    val name: String?,
    val ownerId: Int?,
    val tags: String?
)
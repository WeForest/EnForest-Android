package com.gsm.domain.entity.group.response.search

import com.google.gson.annotations.SerializedName

data class SearchGroupData(
    val chattingId: Int?,
    val description: String?,
    val id: Int?,
    val name: String?,
    val tags: String?,
    val userId: Int?
)
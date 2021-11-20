package com.gsm.data.entity.group.response


import com.google.gson.annotations.SerializedName

data class SearchGroupResponseItem(
    @SerializedName("chattingId")
    val chattingId: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("tags")
    val tags: String?,
    @SerializedName("userId")
    val userId: Int?
)
package com.gsm.data.entity.group.response


import com.google.gson.annotations.SerializedName

data class CreateGroupResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("ownerId")
    val ownerId: Int?,
    @SerializedName("tags")
    val tags: String?
)
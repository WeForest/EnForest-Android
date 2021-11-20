package com.gsm.data.entity.group.response


import com.google.gson.annotations.SerializedName

data class CreateGroupResponse(
    @SerializedName("group")
    val group: Group?,
    @SerializedName("success")
    val success: Boolean?
)
package com.gsm.domain.entity.userinfo


import com.google.gson.annotations.SerializedName

data class ExpLogItem(
    @SerializedName("activity")
    val activity: String,
    @SerializedName("getExp")
    val getExp: Int
)
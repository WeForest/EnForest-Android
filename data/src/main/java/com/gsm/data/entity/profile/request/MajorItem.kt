package com.gsm.data.entity.profile.request


import com.google.gson.annotations.SerializedName

data class MajorItem(
    @SerializedName("major")
    val major: String
)
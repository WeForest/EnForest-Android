package com.gsm.domain.entity.request.profile

import com.google.gson.annotations.SerializedName

data class MajorItem(
    @SerializedName("major")
    val major: String
)
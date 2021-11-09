package com.gsm.domain.entity.request

import com.google.gson.annotations.SerializedName

data class GetProfileRequestEntity (
    @SerializedName("nickname")
    val nickname : String
)
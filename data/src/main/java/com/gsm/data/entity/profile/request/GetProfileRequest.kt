package com.gsm.data.entity.profile.request

import com.google.gson.annotations.SerializedName

data class GetProfileRequest (
    @SerializedName("nickname")
    val nickname : String
)
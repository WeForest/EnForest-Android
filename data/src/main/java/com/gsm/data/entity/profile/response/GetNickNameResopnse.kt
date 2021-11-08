package com.gsm.data.entity.profile.response

import com.google.gson.annotations.SerializedName

data class GetNickNameResopnse (
    @SerializedName("nickname")
    val nickname : String
)
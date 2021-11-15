package com.gsm.data.entity.profile.request

import com.google.gson.annotations.SerializedName

data class InterestsItem (
    @SerializedName("interests")
    val interests: String
)
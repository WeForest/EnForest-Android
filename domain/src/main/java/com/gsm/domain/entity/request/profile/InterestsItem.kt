package com.gsm.domain.entity.request.profile

import com.google.gson.annotations.SerializedName

data class InterestsItem (
    @SerializedName("interests")
    val interests: String
)
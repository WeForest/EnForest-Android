package com.gsm.data.entity.profile.request

import com.google.gson.annotations.SerializedName
import com.gsm.domain.entity.request.profile.Interests
import com.gsm.domain.entity.request.profile.Major

data class PathProfileRequest(
    @SerializedName("nickname")
    val name: String?,
    @SerializedName("purpose")
    val purpose: String?,
    @SerializedName("isJobSeeker")
    val isJobSeeker: Boolean,
    @SerializedName("companyEmail")
    val companyEmail: String?,
    @SerializedName("Major")
    val Major: Major,
    @SerializedName("Major")
    val Interests: Interests

)
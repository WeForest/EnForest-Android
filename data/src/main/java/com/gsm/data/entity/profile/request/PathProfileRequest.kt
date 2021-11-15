package com.gsm.data.entity.profile.request

import com.google.gson.annotations.SerializedName
import com.gsm.domain.entity.request.profile.InterestsItem
import com.gsm.domain.entity.request.profile.MajorItem

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
    val Major: MajorItem,
    @SerializedName("Major")
    val Interests: InterestsItem

)
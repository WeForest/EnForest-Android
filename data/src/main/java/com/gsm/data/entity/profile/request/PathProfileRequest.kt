package com.gsm.data.entity.profile.request

import com.google.gson.annotations.SerializedName


data class PathProfileRequest(

   internal val name: String?,
    val purpose: String?,
    val major: List<Major?>,
    val interested: List<Interests?>,
    val isJobSeeker: Boolean?,
    val companyEmail: String?,

    )

data class Interests(
    val Interested: String
)

data class Major(
    val major: String
)
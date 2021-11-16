package com.gsm.data.entity.profile.request


data class PathProfileRequest(
    val name: String?,
    val purpose: String?,
    val Major: List<Major?>,
    val Interests: List<Interests?>,
    val isJobSeeker: Boolean?,
    val companyEmail: String?,

)
data class Interests(
    val interests: String
)
data class Major(
    val major: String
)
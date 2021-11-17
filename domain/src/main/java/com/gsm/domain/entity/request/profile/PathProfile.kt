package com.gsm.domain.entity.request.profile



data class PathProfile(
    val name: String,
    val purpose: String?,
    val major: MutableList<MajorItem>?,
    val interests: MutableList<InterestsItem>?,
    val isJobSeeker: Boolean?,
    val companyEmail: String?,

    )

data class MajorItem(
    val major: String?
)
data class InterestsItem (
    val interests: String?
)
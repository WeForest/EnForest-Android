package com.gsm.domain.entity.request.profile

data class PathProfile(
    val name : String?,
    val purpose : String?,
    val isJobSeeker : Boolean,
    val companyEmail : String?,
    val Major : Major,
    val Interests : Interests
    )

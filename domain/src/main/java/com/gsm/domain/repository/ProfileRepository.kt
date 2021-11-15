package com.gsm.domain.repository

import com.gsm.domain.entity.request.profile.Interests
import com.gsm.domain.entity.request.profile.Major
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity

interface ProfileRepository {

    suspend fun getProfile(nickname : String) : GetProfileEntity
    suspend fun pathProfile(
        name : String?,
        purpose : String?,
        isJobSeeker : Boolean,
        companyEmail : String?,
        Major : Major,
        Interests : Interests
    ) : PathProfileEntity
}
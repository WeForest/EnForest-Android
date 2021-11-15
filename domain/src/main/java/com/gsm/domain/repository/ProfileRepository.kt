package com.gsm.domain.repository

import com.gsm.domain.entity.request.profile.InterestsItem
import com.gsm.domain.entity.request.profile.MajorItem
import com.gsm.domain.entity.request.profile.PathProfile
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity

interface ProfileRepository {

    suspend fun getProfile(nickname : String) : GetProfileEntity
    suspend fun pathProfile(pathProfile : PathProfile) : PathProfileEntity
}
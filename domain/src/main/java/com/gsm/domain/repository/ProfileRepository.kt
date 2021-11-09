package com.gsm.domain.repository

import com.gsm.domain.entity.request.GetProfileRequestEntity
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity

interface ProfileRepository {

    suspend fun getProfile(request : GetProfileRequestEntity) : GetProfileEntity
    suspend fun pathProfile(authorization: String) : PathProfileEntity
}
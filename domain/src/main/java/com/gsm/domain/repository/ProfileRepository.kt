package com.gsm.domain.repository

import com.gsm.domain.entity.group.response.BaseEntity
import com.gsm.domain.entity.request.profile.PathProfile
import com.gsm.domain.entity.response.GetProfileEntity
import com.gsm.domain.entity.response.PathProfileEntity
import okhttp3.MultipartBody

interface ProfileRepository {

    suspend fun getProfile(nickname : String) : GetProfileEntity
    suspend fun pathProfile(token:String,pathProfile : PathProfile) : PathProfileEntity
    suspend fun postProfile(token:String, file: MultipartBody.Part?) : BaseEntity
}
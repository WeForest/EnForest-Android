package com.gsm.data.datasource.profile


import com.gsm.data.entity.profile.request.PathProfileRequest
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse

interface  ProfileDataSource {
    suspend fun getProfile(nickname : String): GetProfileResponse
    suspend fun pathProfile(token:String,request : PathProfileRequest): PathProfileResponse
}
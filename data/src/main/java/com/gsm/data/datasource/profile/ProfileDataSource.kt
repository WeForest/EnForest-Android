package com.gsm.data.datasource.profile

import com.gsm.data.entity.profile.request.GetProfileRequest
import com.gsm.data.entity.profile.response.PathProfileResponse
import com.gsm.data.entity.profile.response.GetProfileResponse

interface  ProfileDataSource {
     suspend fun getProfile(request: GetProfileRequest): GetProfileResponse
     suspend fun pathProfile(authorization: String): PathProfileResponse
}
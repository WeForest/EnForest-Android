package com.gsm.data.datasource.profile


import com.gsm.data.entity.group.response.BaseResponse
import com.gsm.data.entity.profile.request.PathProfileRequest
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import okhttp3.MultipartBody

interface  ProfileDataSource {
    suspend fun getProfile(nickname : String): GetProfileResponse
    suspend fun pathProfile(token:String,request : PathProfileRequest): PathProfileResponse
    suspend fun postProfile(token: String, file: MultipartBody.Part?): BaseResponse

}
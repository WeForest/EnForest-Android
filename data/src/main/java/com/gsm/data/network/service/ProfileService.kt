package com.gsm.data.network.service

import com.gsm.data.entity.group.response.BaseResponse
import com.gsm.data.entity.profile.request.PathProfileRequest
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileService {

    @GET("/profile/{nickname}")
    suspend fun viewProfile(
        @Path("nickname") nickname: String
    ): GetProfileResponse

    @PATCH("/profile/update")
    suspend fun pathProfile(
        @Header("authorization") token: String,
        @Body request: PathProfileRequest
    ): PathProfileResponse

    @Multipart
    @POST("profile/picture")
    suspend fun postProfile(
        @Header("authorization") token: String,
        @Part file: MultipartBody.Part?,
        ):BaseResponse

}
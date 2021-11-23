package com.gsm.data.network.service

import com.gsm.data.entity.group.response.BaseResponse
import com.gsm.data.entity.profile.request.PathProfileRequest
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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
        // content   q
        @Header("authorization") token: String,
        @Part("file") file: MultipartBody.Part,
        ):BaseResponse

}
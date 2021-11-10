package com.gsm.data.network.service

import com.gsm.data.entity.profile.request.GetProfileRequest
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import retrofit2.http.*

interface ProfileService {

    @GET("/profile/{nickname}")
    suspend fun viewProfile(
        @Path("nickname") request : GetProfileRequest
    ): GetProfileResponse

    @PATCH("/profile/update")
    suspend fun pathProfile(
        @Header("authorization") authorization: String
    ) : PathProfileResponse

}
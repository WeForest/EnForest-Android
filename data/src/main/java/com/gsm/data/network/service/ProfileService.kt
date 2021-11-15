package com.gsm.data.network.service

import com.gsm.data.entity.profile.request.PathProfileRequest
import com.gsm.data.entity.profile.response.GetProfileResponse
import com.gsm.data.entity.profile.response.PathProfileResponse
import retrofit2.http.*

interface ProfileService {

    @GET("/profile/{nickname}")
    suspend fun viewProfile(
        @Path("nickname") nickname : String
    ): GetProfileResponse

    @PATCH("/profile/update")
    suspend fun pathProfile(
        @Body request : PathProfileRequest
    ) : PathProfileResponse

}